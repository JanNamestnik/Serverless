type Category = {
  _id: { $oid: string };
  name: string;
};

const categories: Category[] = [
  { _id: { $oid: "66423238ff14c4a754772598" }, name: "concert" },
  { _id: { $oid: "66423250ff14c4a754772599" }, name: "festival" },
  { _id: { $oid: "6642325dff14c4a75477259a" }, name: "sport" },
  { _id: { $oid: "6642328eff14c4a75477259c" }, name: "community event" },
  { _id: { $oid: "664232a0ff14c4a75477259d" }, name: "educational event" },
  { _id: { $oid: "664232b5ff14c4a75477259e" }, name: "performance" },
  { _id: { $oid: "66423365ff14c4a75477259f" }, name: "conference" },
  { _id: { $oid: "6643ef1e35e389b1272f6b82" }, name: "exhibition" },
  { _id: { $oid: "6643ef3e35e389b1272f6b83" }, name: "other" },
];

export { categories };
