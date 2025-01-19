package si.um.feri.serverless;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;

import si.um.feri.serverless.assets.AssetDescriptors;
import si.um.feri.serverless.assets.AssetManager;
import si.um.feri.serverless.assets.RegionNames;
import si.um.feri.serverless.config.GameConfig;
import si.um.feri.serverless.screen.ContinueScreen;
import si.um.feri.serverless.utils.Constants;
import si.um.feri.serverless.utils.Geolocation;
import si.um.feri.serverless.utils.MapRasterTiles;
import si.um.feri.serverless.utils.ZoomXY;

import com.badlogic.gdx.utils.viewport.Viewport;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Map extends ApplicationAdapter implements GestureDetector.GestureListener {

    private ShapeRenderer shapeRenderer;

    private Viewport viewport;

    private float rotationAngle = 0f;

    private Skin skin;

    private Skin skin_alternative;

    private Vector2 selectedMarkerPosition = null; // Pozicija kliknjenega markerja
    private boolean isFieldVisible = false; // Ali je polje prikazano

    private Vector3 mousePosition = new Vector3();

    private TextureAtlas gameplayAtlas;

    private SpriteBatch spriteBatch;
    private TextureRegion pinRegion;
    private AssetManager assetManager;
    private Vector3 touchPosition;

    private List<Geolocation> eventLocations;

    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    private OrthographicCamera camera;

    private Texture[] mapTiles;
    private ZoomXY beginTile;   // top left tile

    private Label clickMeLabel;

    private Label playLabel;

    private Stage stage;


    // center geolocation
    private final Geolocation CENTER_GEOLOCATION = new Geolocation(46.557314, 15.637771);

    @Override
    public void create() {
        stage = new Stage();
        shapeRenderer = new ShapeRenderer();
        spriteBatch = new SpriteBatch();

        assetManager = new AssetManager();
        assetManager.loadAllAssets();
        assetManager.finishLoading();
        gameplayAtlas = assetManager.getGameplayAtlas();

        pinRegion = gameplayAtlas.findRegion(RegionNames.PIN);

        BitmapFont uiFont = assetManager.get(AssetDescriptors.MAP_FONT); // Load the UI_FONT

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = uiFont; // Set the font to the label style

        clickMeLabel = new Label("Click me!", labelStyle); // Use the label style
        playLabel = new Label("Play", labelStyle);

        // Add InputListener to playLabel
        playLabel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
               addHoverAnimation(playLabel);
                return true;
            }
        });

        skin_alternative = assetManager.getAlternativeSkin(AssetDescriptors.UI_SKIN_ALTERNATIVE);

        GestureDetector gestureDetector = new GestureDetector(this);
        Gdx.input.setInputProcessor(gestureDetector);

        camera = new OrthographicCamera();

        camera.setToOrtho(false, Constants.MAP_WIDTH, Constants.MAP_HEIGHT);
        camera.position.set(Constants.MAP_WIDTH / 2f, Constants.MAP_HEIGHT / 2f, 0);
        camera.viewportWidth = Constants.MAP_WIDTH / 2f;
        camera.viewportHeight = Constants.MAP_HEIGHT / 2f;
        camera.zoom = 2f;
        camera.update();

        touchPosition = new Vector3();

        try {
            ZoomXY centerTile = MapRasterTiles.getTileNumber(CENTER_GEOLOCATION.lat, CENTER_GEOLOCATION.lng, Constants.ZOOM);
            mapTiles = MapRasterTiles.getRasterTileZone(centerTile, Constants.NUM_TILES);
            beginTile = new ZoomXY(Constants.ZOOM, centerTile.x - ((Constants.NUM_TILES - 1) / 2), centerTile.y - ((Constants.NUM_TILES - 1) / 2));
        } catch (IOException e) {
            e.printStackTrace();
        }

        tiledMap = new TiledMap();
        MapLayers layers = tiledMap.getLayers();

        TiledMapTileLayer layer = new TiledMapTileLayer(Constants.NUM_TILES, Constants.NUM_TILES, MapRasterTiles.TILE_SIZE, MapRasterTiles.TILE_SIZE);
        int index = 0;
        for (int j = Constants.NUM_TILES - 1; j >= 0; j--) {
            for (int i = 0; i < Constants.NUM_TILES; i++) {
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                cell.setTile(new StaticTiledMapTile(new TextureRegion(mapTiles[index], MapRasterTiles.TILE_SIZE, MapRasterTiles.TILE_SIZE)));
                layer.setCell(i, j, cell);
                index++;
            }
        }
        layers.add(layer);

        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        fetchEvents(events -> {
            eventLocations = events;
        });

        showDetailsWindow("Event Name", "Event Details", "Address", "Start Time", "Contact", "Date Start");
    }

    private void addHoverAnimation(Label label) {
        label.clearActions(); // Clear any existing actions
        label.addAction(Actions.sequence(
            Actions.alpha(0),
            Actions.fadeIn(0.5f)
        ));
    }

    // Fetch events from MongoDB
    private void fetchEvents(OnResultCallback<List<Geolocation>> callback) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
            .url("https://eu-central-1.aws.data.mongodb-api.com/app/serverlessapi-uvgsfoc/endpoint/events")
            .addHeader("Accept-Charset", "UTF-8")
            .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.body() != null) {
                    String jsonResponse = response.body().string();
                    List<Geolocation> events = parseEventsFromJson(jsonResponse);
                    callback.onResult(events);
                }
            }
        });
    }


    private List<Geolocation> parseEventsFromJson(String jsonResponse) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Event>>() {}.getType();
        List<Event> events = gson.fromJson(jsonResponse, listType);

        List<Geolocation> geolocations = new ArrayList<>();
        for (Event event : events) {
            double lat = event.location.coordinates.get(1);
            double lng = event.location.coordinates.get(0);
            Geolocation geolocation = new Geolocation(lat, lng);
            geolocation.setName(event.name); // Set the event name
            geolocation.setAddress(event.address); // Set the event address
            geolocation.setStartTime(event.startTime); // Set the event start time
            geolocation.setContact(event.contact); // Set the event contact
            geolocation.setDateStart(event.date_start); // Set the event start date
            geolocations.add(geolocation);
        }
        return geolocations;
    }
    class Event {
        String name;
        String address;
        String startTime;
        String contact;
        String date_start;
        Location location;

        class Location {
            List<Double> coordinates;
        }
    }

    private void drawMarkers() {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();

        // Update rotation angle once per frame
        rotationAngle += 3f; // Rotation speed

        if (eventLocations != null) {
            for (Geolocation location : eventLocations) {
                Vector2 marker = MapRasterTiles.getPixelPosition(location.lat, location.lng, beginTile.x, beginTile.y);

                // Check if marker is within camera's view
                if (camera.frustum.pointInFrustum(marker.x, marker.y, 0)) {
                    if (isMouseOverMarker(marker)) {
                        float bounceOffset = MathUtils.sinDeg(rotationAngle * 2) * 10; // Bouncing effect

                        // Draw pin with rotation around its center
                        spriteBatch.draw(pinRegion,
                            marker.x - (float) pinRegion.getRegionWidth() / 2, // X start position
                            marker.y - (float) pinRegion.getRegionHeight() / 2 + bounceOffset, // Y with bounce offset
                            (float) pinRegion.getRegionWidth() / 2, // X center of rotation
                            (float) pinRegion.getRegionHeight() / 2, // Y center of rotation
                            pinRegion.getRegionWidth(), // Image width
                            pinRegion.getRegionHeight(), // Image height
                            1f, // Scale X
                            1f, // Scale Y
                            rotationAngle // Rotation angle
                        );
                    } else {
                        spriteBatch.draw(pinRegion,
                            marker.x - (float) pinRegion.getRegionWidth() / 2,
                            marker.y - (float) pinRegion.getRegionHeight() / 2);
                    }
                }
            }
        }

        spriteBatch.end();
    }
    private void drawHoverText(Vector2 marker, String eventName) {
        spriteBatch.begin();
        // Draw label
        clickMeLabel.setText(eventName); // Set the event name as the label text
        clickMeLabel.setPosition(marker.x - clickMeLabel.getWidth() / 2, marker.y + 20);
        clickMeLabel.draw(spriteBatch, 1);
        spriteBatch.end();
    }

    private boolean isClickOnMarker(Vector2 marker, float x, float y) {
        float markerWidth = pinRegion.getRegionWidth();
        float markerHeight = pinRegion.getRegionHeight();
        return x >= marker.x - markerWidth / 2 && x <= marker.x + markerWidth / 2 &&
            y >= marker.y - markerHeight / 2 && y <= marker.y + markerHeight / 2;
    }

    private void removeDetailsWindow() {
        for (Actor actor : stage.getActors()) {
            if (actor instanceof Window && "DetailsWindow".equals(actor.getName())) {
                actor.remove();
                break;
            }
        }
    }

    private void showDetailsWindow(String eventName, String eventDetails, String address, String startTime, String contact, String dateStart) {
        // Retrieve the fonts from the asset manager
        BitmapFont fontTitle = assetManager.get(AssetDescriptors.MAP_FONT);
        BitmapFont fontText = assetManager.get(AssetDescriptors.MAP_HUD_BLANK_FONT);

        // Apply scaling to the title font
        fontTitle.getData().setScale(0.7f); // Scale down to 60% of the original size
        fontText.getData().setScale(0.5f); // Scale down to 60% of the original size

        // Create a new window style
        Window.WindowStyle windowStyle = new Window.WindowStyle();
        windowStyle.titleFont = fontTitle; // Use the scaled title font

        // Ensure the region exists in the atlas
        TextureRegion region = assetManager.getGameplayAtlas().findRegion(RegionNames.GAMEPLAY_HUD);

        windowStyle.background = new TextureRegionDrawable(region);

        Window detailsWindow = new Window("", windowStyle);
        detailsWindow.setName("DetailsWindow"); // Set the name for the window
        detailsWindow.setSize(400, 300);
        detailsWindow.setPosition(30, GameConfig.HEIGHT - detailsWindow.getHeight() - 40);

        // Create labels for the event details
        Label titleLabel = new Label(eventName, new Label.LabelStyle(fontTitle, Color.WHITE));
        titleLabel.setPosition(20, detailsWindow.getHeight() - 40); // Adjust the Y position as needed

        Label addressLabel = new Label("Address: " + address, new Label.LabelStyle(fontText, Color.WHITE));
        addressLabel.setPosition(20, detailsWindow.getHeight() - 70);

        String startTimeText = (startTime != null) ? startTime : "start time was not defined";
        Label startTimeLabel = new Label("Start Time: " + startTimeText, new Label.LabelStyle(fontText, Color.WHITE));
        startTimeLabel.setPosition(20, detailsWindow.getHeight() - 100);

        Label contactLabel = new Label("Contact: " + contact, new Label.LabelStyle(fontText, Color.WHITE));
        contactLabel.setPosition(20, detailsWindow.getHeight() - 130);

        Label dateStartLabel = new Label("Date Start: " + dateStart, new Label.LabelStyle(fontText, Color.WHITE));
        dateStartLabel.setPosition(20, detailsWindow.getHeight() - 160);

        // Add the labels to the window
        detailsWindow.addActor(titleLabel);
        detailsWindow.addActor(addressLabel);
        detailsWindow.addActor(startTimeLabel);
        detailsWindow.addActor(contactLabel);
        detailsWindow.addActor(dateStartLabel);

        // Add the window to the stage
        stage.addActor(detailsWindow);
    }


    private void updateMousePosition() {
        mousePosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePosition);
    }

    private boolean isMouseOverMarker(Vector2 marker) {
        float markerWidth = pinRegion.getRegionWidth();
        float markerHeight = pinRegion.getRegionHeight();
        return mousePosition.x >= marker.x - markerWidth / 2 && mousePosition.x <= marker.x + markerWidth / 2 &&
            mousePosition.y >= marker.y - markerHeight / 2 && mousePosition.y <= marker.y + markerHeight / 2;
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 1);

        handleInput();

        camera.update();

        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        updateMousePosition();
        drawMarkers();

        boolean isHovering = false;

        // Only execute hover functions if the white field is not visible
        if (!isFieldVisible && eventLocations != null) {
            for (Geolocation location : eventLocations) {
                Vector2 marker = MapRasterTiles.getPixelPosition(location.lat, location.lng, beginTile.x, beginTile.y);
                if (isMouseOverMarker(marker)) {
                    drawHoverText(marker, location.getName()); // Pass the event name
                    removeDetailsWindow(); // Remove the previous window
                    showDetailsWindow(location.getName(), location.getName(), location.getAddress(), location.getStartTime(), location.getContact(), location.getStartDate());
                    isHovering = true;
                    break;
                }
            }
        }

        if (!isHovering) {
            removeDetailsWindow(); // Remove the window if not hovering over any marker
        }

        // Update and draw the stage
        stage.act();
        stage.draw();
    }
    @Override
    public void dispose() {
        shapeRenderer.dispose();
        spriteBatch.dispose();
        assetManager.dispose();
        stage.dispose();
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        touchPosition.set(x, y, 0);
        camera.unproject(touchPosition);
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        touchPosition.set(x, y, 0);
        camera.unproject(touchPosition);

        if (eventLocations != null) {
            for (Geolocation location : eventLocations) {
                Vector2 marker = MapRasterTiles.getPixelPosition(location.lat, location.lng, beginTile.x, beginTile.y);
                if (isClickOnMarker(marker, touchPosition.x, touchPosition.y)) {
                    // Preklop na DiscountGame
                    GameWrapper gameWrapper = (GameWrapper) Gdx.app.getApplicationListener();
                    gameWrapper.switchToGame(); // Preklopi na DiscountGame
                    ((DiscountGame) gameWrapper.getGame()).setScreen(new ContinueScreen((DiscountGame) gameWrapper.getGame()));
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        camera.translate(-deltaX, deltaY);
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        if (initialDistance >= distance)
            camera.zoom += 0.02;
        else
            camera.zoom -= 0.02;
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera.zoom += 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            camera.zoom -= 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.translate(-3, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.translate(3, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.translate(0, -3, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.translate(0, 3, 0);
        }

        camera.zoom = MathUtils.clamp(camera.zoom, 0.5f, 2f);

        float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
        float effectiveViewportHeight = camera.viewportHeight * camera.zoom;

        camera.position.x = MathUtils.clamp(camera.position.x, effectiveViewportWidth / 2f, Constants.MAP_WIDTH - effectiveViewportWidth / 2f);
        camera.position.y = MathUtils.clamp(camera.position.y, effectiveViewportHeight / 2f, Constants.MAP_HEIGHT - effectiveViewportHeight / 2f);
    }

    @FunctionalInterface
    interface OnResultCallback<T> {
        void onResult(T result);
    }


}
