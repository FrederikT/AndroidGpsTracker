package ie.griffith.thuss.gpstracker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

import static ie.griffith.thuss.gpstracker.R.color.colorAccent;
import static ie.griffith.thuss.gpstracker.R.color.colorAdditionalDark;
import static ie.griffith.thuss.gpstracker.R.color.colorNegativeAccent;
import static ie.griffith.thuss.gpstracker.R.color.colorPositiveAccent;
import static ie.griffith.thuss.gpstracker.R.color.colorPrimaryDark;

public class SpeedGraph extends View {
    static Speed speed;
    private Paint paint = new Paint();

    public SpeedGraph(Context c) { super(c); }
    public SpeedGraph(Context c, AttributeSet as) { super(c, as); }
    public SpeedGraph(Context c, AttributeSet as, int default_style) { super(c, as, default_style); }

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

        if (!speed.getSpeedList().isEmpty()) {

            /* Compute bars width & set stroke */
            int barWidth = width / speed.getSpeedList().size();
            paint.setStrokeWidth(barWidth);

            float unit;
            if(speed.getMax() > Float.MAX_VALUE){
                unit = height / Float.MAX_VALUE;
            }else{
                unit =  height / Float.valueOf(String.valueOf(speed.getMax()));
            }

            /* Graph margin workaround */
            float offset = barWidth / 2;

            /* Draw line, switch colour & advance offset to draw next bar */
            for (Double speedListItem : speed.getSpeedList()) { /* Convert lonfloatg to pixels */
                float speedHeight;
                if((double) speedListItem > Float.MAX_VALUE){
                    speedHeight =  Float.MAX_VALUE;
                }else{
                    speedHeight =  Float.valueOf(String.valueOf((double)speedListItem));
                }
                Double slowPace = speed.getAverage() - ((speed.getAverage() - speed.getMin())/2);
                Double fastPace = speed.getAverage() + ((speed.getMax() - speed.getAverage())/2);
                if(speedListItem >= fastPace){
                    paint.setColor(this.getResources().getColor(colorPositiveAccent));
                }else if(speedListItem <= slowPace){
                    paint.setColor(this.getResources().getColor(colorNegativeAccent));
                }else{
                    paint.setColor(this.getResources().getColor(colorAdditionalDark));
                }
                canvas.drawLine(offset, height - (unit * speedHeight), offset, height, paint);

                offset += barWidth;
            }
            paint.setTextSize(100);
            paint.setColor(R.color.colorAdditionalBright);
            canvas.drawText("Speed", (float) (width*0.5), height, paint);
        }
    }




}