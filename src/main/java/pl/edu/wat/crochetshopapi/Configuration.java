package pl.edu.wat.crochetshopapi;

import pl.edu.wat.crochetshopapi.model.Image;

public class Configuration {
    /**
     * Directory to save uploaded images.
     */
    public static final String IMAGES_PATH = "./img/";
    public static final Image DEFAULT_IMAGE = null;
    public static final int PROMOCODE_LENGTH = 8;

    /**
     * Delay time between posting comments in minutes.
     */
    public static final long COMMENT_DELAY_TIME = 1;
}
