package edu.Moravian.Math;

import edu.Moravian.Math.Point2D;
import java.awt.Point;

/**
 * @author mebjc01
 *
 * This class coordinates the translation of points on the screen
 * to points in the world.  The screen is measured in pixels (int)
 * while the world can be measured in an arbitrary number of any units
 * (double).  In addition, the y axis is inverted from *normal* on
 * the screen.  Finally, (0, 0) is the top-left of the screen but
 * may occur in an arbitrary location in the world (it may not even
 * end up on the screen).  Thus, this class performs all the
 * translations, inversions, and conversions necessary.
 */
public class CoordinateTranslator
{
    private int screenWidth;
    private int screenHeight;
    private double worldWidth;
    private double worldHeight;

    // The ratios of world and screen, width and height are
    // used extensively in the conversions.  Because this class
    // is likely to be used frequently, we store these ratios
    // to avoid repeated computation.
    private double ww2sw;
    private double sw2ww;
    private double wh2sh;
    private double sh2wh;

    // The Offset of the world measures how far (0, 0) is from the
    // lower left.  This is also used in the computations.
    private double hortOffset;
    private double vertOffset;

    /**
     * Initialize all the variables so that we are ready to convert points.
     *
     * @param screenWidth width of the screen in pixels.
     * @param screenHeight height of the screen in pixels.
     * @param worldWidth width of the world (in whatever units you want)
     * @param worldHeight height of the world (presumably thesame units
     *  as worldWidth)
     * @param lowerLeft the lower left point of the visible world
     */
    public CoordinateTranslator(int screenWidth, int screenHeight,
            double worldWidth, double worldHeight,
            Point2D lowerLeft)
    {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;

        hortOffset = lowerLeft.getX();
        vertOffset = lowerLeft.getY();
        
        ww2sw = worldWidth / screenWidth;
        sw2ww = screenWidth / worldWidth;
        wh2sh = worldHeight / screenHeight;
        sh2wh = screenHeight / worldHeight;

        if(Math.abs(ww2sw - wh2sh) > 0.001)
            throw new IllegalArgumentException("world to screen ratios should be equal for both width and height.");
    }

    /**
     * Convert an integer-valued point on the screen to the corresponding point
     * in the world.  Note that the screen is discrete pixels while the world
     * is continuous.  Therefore all world points are not possible.
     * @param screenPoint the point on the screen
     * @return the corresponding point in the world
     */
    public Point2D screenToWorld(Point screenPoint)
    {
        return new Point2D(ww2sw * screenPoint.x + hortOffset,
                worldHeight - wh2sh * screenPoint.y + vertOffset);
    }

    /**
     * Convert a real-valued point in the world to the corresponding point
     * on the screen.  Note that the screen is pixels, and so that
     * point must be discretized.  This method uses truncation to
     * select the point.
     * 
     * @param worldPoint the point in the world
     * @return the corresponding point (pixel) on the screen
     */
    public Point worldToScreen(Point2D worldPoint)
    {
        Point ret = new Point();

        ret.x = (int)(sw2ww * (worldPoint.getX() - hortOffset));
        ret.y = (int)(screenHeight - sh2wh * (worldPoint.getY() - vertOffset));

        return ret;
    }

    /**
     * Convert a distance in the world to the corresponding distance on the
     * screen.
     * @param d
     * @return
     */
    public double worldToScreenDistance(double d)
    {
        return d * sw2ww;
    }
}
