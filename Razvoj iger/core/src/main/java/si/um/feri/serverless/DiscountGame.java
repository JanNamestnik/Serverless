package si.um.feri.serverless;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
import com.badlogic.gdx.utils.ScreenUtils;

import si.um.feri.serverless.assets.AssetManager;
import si.um.feri.serverless.assets.RegionNames;
import si.um.feri.serverless.utils.Constants;
import si.um.feri.serverless.utils.Geolocation;
import si.um.feri.serverless.utils.MapRasterTiles;
import si.um.feri.serverless.utils.ZoomXY;

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

public class DiscountGame extends ApplicationAdapter implements GestureDetector.GestureListener {

    private ShapeRenderer shapeRenderer;

    private float rotationAngle = 0f;

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

    // center geolocation
    private final Geolocation CENTER_GEOLOCATION = new Geolocation(46.557314, 15.637771);

    @Override
    public void create() {

        shapeRenderer = new ShapeRenderer();

        spriteBatch = new SpriteBatch();

        AssetManager assetManager = new AssetManager();
        assetManager.loadAllAssets();
        assetManager.finishLoading();
        gameplayAtlas = assetManager.getGameplayAtlas();

        pinRegion = gameplayAtlas.findRegion(RegionNames.PIN);


        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.MAP_WIDTH, Constants.MAP_HEIGHT);
        camera.position.set(Constants.MAP_WIDTH / 2f, Constants.MAP_HEIGHT / 2f, 0);
        camera.viewportWidth = Constants.MAP_WIDTH / 2f;
        camera.viewportHeight = Constants.MAP_HEIGHT / 2f;
        camera.zoom = 2f;
        camera.update();

        touchPosition = new Vector3();

        try {
            //in most cases, geolocation won't be in the center of the tile because tile borders are predetermined (geolocation can be at the corner of a tile)
            ZoomXY centerTile = MapRasterTiles.getTileNumber(CENTER_GEOLOCATION.lat, CENTER_GEOLOCATION.lng, Constants.ZOOM);
            mapTiles = MapRasterTiles.getRasterTileZone(centerTile, Constants.NUM_TILES);
            //you need the beginning tile (tile on the top left corner) to convert geolocation to a location in pixels.
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
    }

    // Fetch events from MongoDB
    private void fetchEvents(OnResultCallback<List<Geolocation>> callback) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
            .url("https://eu-central-1.aws.data.mongodb-api.com/app/serverlessapi-uvgsfoc/endpoint/events")
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


    // Parse events from JSON, extract latitude and longitude
    private List<Geolocation> parseEventsFromJson(String jsonResponse) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Event>>() {}.getType();
        List<Event> events = gson.fromJson(jsonResponse, listType);

        List<Geolocation> geolocations = new ArrayList<>();
        for (Event event : events) {
            double lat = event.location.coordinates.get(1);
            double lng = event.location.coordinates.get(0);
            geolocations.add(new Geolocation(lat, lng));
        }
        return geolocations;
    }

    class Event {
        Location location;

        class Location {
            List<Double> coordinates;
        }
    }

    private void drawMarkers() {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();

        if (eventLocations != null) {
            for (Geolocation location : eventLocations) {
                Vector2 marker = MapRasterTiles.getPixelPosition(location.lat, location.lng, beginTile.x, beginTile.y);

                if (isMouseOverMarker(marker)) {
                    rotationAngle += 3f; // Hitrost vrtenja

                    float bounceOffset = MathUtils.sinDeg(rotationAngle * 2) * 5; // Poskakovanje gor in dol

                    // Prikaz pina z rotacijo okoli lastnega središča
                    spriteBatch.draw(pinRegion,
                        marker.x - pinRegion.getRegionWidth() / 2, // X začetne pozicije
                        marker.y - pinRegion.getRegionHeight() / 2 + bounceOffset, // Y z dodatkom za poskakovanje
                        pinRegion.getRegionWidth() / 2, // X središča rotacije
                        pinRegion.getRegionHeight() / 2, // Y središča rotacije
                        pinRegion.getRegionWidth(), // Širina slike
                        pinRegion.getRegionHeight(), // Višina slike
                        1f, // Merilo po X
                        1f, // Merilo po Y
                        rotationAngle // Kot rotacije
                    );
                } else {
                    spriteBatch.draw(pinRegion,
                        marker.x - pinRegion.getRegionWidth() / 2,
                        marker.y - pinRegion.getRegionHeight() / 2);
                }
            }
        }

        spriteBatch.end();
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
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        spriteBatch.dispose();
        assetManager.dispose();
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        touchPosition.set(x, y, 0);
        camera.unproject(touchPosition);
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
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
