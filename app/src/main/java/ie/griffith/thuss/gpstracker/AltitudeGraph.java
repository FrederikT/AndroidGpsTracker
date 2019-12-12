package ie.griffith.thuss.gpstracker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class AltitudeGraph extends View {
    static Altitude altitude;

    public AltitudeGraph(Context c) { super(c); }
    public AltitudeGraph(Context c, AttributeSet as) { super(c, as); }
    public AltitudeGraph(Context c, AttributeSet as, int default_style) { super(c, as, default_style); }

    public void onMeasure(int width, int height) {

        /* Force graph to be square */
        if (width < height)
            super.onMeasure(width, width);
        else super.onMeasure(height, height);
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /* Get graph size */
        int width = getMeasuredWidth();
        float height = getMeasuredHeight();

        /* Draw graph if at least 1 km has been done */

        if (!altitude.getAltitudeList().isEmpty()) {

            /* Compute bars width & set stroke */
            int barWidth = width / altitude.getAltitudeList().size();
            paint.setStrokeWidth(barWidth);

            /* Compute number of pixels for 1ms */

            float unit;
            if(altitude.getMax() > Float.MAX_VALUE){
                unit = height / Float.MAX_VALUE;
            }else{
                unit =  height / Float.valueOf(String.valueOf(altitude.getMax()));
            }


            /* Graph margin workaround */
            float offset = barWidth / 2;

            /* Draw line, switch colour & advance offset to draw next bar */
            for (Double altitudeListItem : altitude.getAltitudeList()) { /* Convert float to pixels */
                float altHeight;
                if((double) altitudeListItem > Float.MAX_VALUE){
                    altHeight =  Float.MAX_VALUE;
                }else{
                    altHeight =  Float.valueOf(String.valueOf((double)altitudeListItem));
                }



                paint.setColor(Color.BLUE);
                canvas.drawLine(offset, height - (unit * altHeight), offset, height, paint);

                offset += barWidth;
            }
        }
    }

    /* Graph appearance variables */
    private Paint paint = new Paint();


}