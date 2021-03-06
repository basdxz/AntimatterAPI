package muramasa.antimatter.structure;

import muramasa.antimatter.registration.IAntimatterObject;
import muramasa.antimatter.util.int3;
import net.minecraft.util.Tuple;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class StructureBuilder {

    private static HashMap<String, StructureElement> globalElementLookup = new HashMap<>();

    private ArrayList<String[]> slices = new ArrayList<>();
    private HashMap<String, StructureElement> elementLookup = new HashMap<>();

    //TODO, probably move to StructureElement?
    public static void addGlobalElement(String key, StructureElement element) {
        globalElementLookup.put(key, element);
    }

    public StructureBuilder of(String... slices) {
        this.slices.add(slices);
        return this;
    }

    public StructureBuilder of(int i) {
        slices.add(slices.get(i));
        return this;
    }

    public StructureBuilder at(String key, StructureElement element) {
        elementLookup.put(key, element);
        return this;
    }

    public StructureBuilder at(String key, IAntimatterObject... objects) {
        elementLookup.put(key, new ComponentElement(objects));
        return this;
    }

    public StructureBuilder at(String key, String name, IAntimatterObject... objects) {
        elementLookup.put(key, new ComponentElement(name, objects));
        return this;
    }

    public StructureBuilder at(String key, Collection<? extends IAntimatterObject> objects) {
        elementLookup.put(key, new ComponentElement(objects.toArray(new IAntimatterObject[0])));
        return this;
    }

    public StructureBuilder at(String key, String name, Collection<? extends IAntimatterObject> objects) {
        elementLookup.put(key, new ComponentElement(name, objects.toArray(new IAntimatterObject[0])));
        return this;
    }

    public Structure build() {
        ArrayList<Tuple<int3, StructureElement>> elements = new ArrayList<>();
        int3 size = new int3(slices.get(0).length, slices.size(), slices.get(0)[0].length());
        StructureElement e;
        for (int y = 0; y < size.getY(); y++) {
            for (int x = 0; x < size.getX(); x++) {
                for (int z = 0; z < size.getZ(); z++) {
                    e = elementLookup.get(slices.get(y)[x].substring(z, z + 1));
                    if (e == null) e = globalElementLookup.get(slices.get(y)[x].substring(z, z + 1));
                    //TODO log this and return null;
                    if (e == null) throw new NullPointerException("StructureBuilder failed to parse slice: " + slices.get(y)[x]);
                    if (e.exclude) continue;
                    elements.add(new Tuple<>(new int3(x, y, z), e));
                }
            }
        }
        return new Structure(size, elements);
    }
}
