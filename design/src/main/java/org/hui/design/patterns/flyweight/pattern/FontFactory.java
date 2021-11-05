package org.hui.design.patterns.flyweight.pattern;

import java.util.Map;

public class FontFactory {
    private Map<String, Font> fontPool;

    public Font getFont(String key) {
        Font result = null;
        _return:
        for (String fontKey : fontPool.keySet()) {
            if (fontKey == key) {
                result = fontPool.get(fontKey);
                break _return;
            } else {
                Font font = new Font(key);
                fontPool.put(key, font);
                result =  font;
                break _return;
            }
        }
        return result;
    }
}
