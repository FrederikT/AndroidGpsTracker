package ie.griffith.thuss.gpstracker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import static ie.griffith.thuss.gpstracker.R.color.colorAdditionalBright;
import static ie.griffith.thuss.gpstracker.R.color.colorAdditionalDark;
import static ie.griffith.thuss.gpstracker.R.color.colorNegativeAccent;
import static ie.griffith.thuss.gpstracker.R.color.colorPositiveAccent;

public class TrackingAttributeGraph extends View {
    public TrackingAttribute trackingAttribute;
    public boolean markExtremes = false;
    public String graphDescription ="";
    private Paint paint = new Paint();


    public TrackingAttributeGraph(Context c) { super(c); }
    public TrackingAttributeGraph(Context c, AttributeSet as) { super(c, as); }
    public TrackingAttributeGraph(Context c, AttributeSet as, int default_style) { super(c, as, default_style); }

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

        if (!trackingAttribute.getList().isEmpty()) {

            /* Compute bars width & set stroke */
            int barWidth = width / trackingAttribute.getList().size();
            paint.setStrokeWidth(barWidth);

            float unit;
            // Just as safety because double - float cast necessary and normally not possible
            if(trackingAttribute.getMax() > Float.MAX_VALUE){
                unit = height / Float.MAX_VALUE;
            }else{
                unit =  height / Float.valueOf(String.valueOf(trackingAttribute.getMax()));
            }

            /* Graph margin workaround */
            float offset = barWidth / 2;

            /* Draw line, switch colour & advance offset to draw next bar */
            for (Double trackingAttributeListItem : trackingAttribute.getList()) {
                /* Convert float to pixels */
                float trackingAttributeHeight;
                // Just as safety because double - float cast necessary and normally not possible
                if(trackingAttributeListItem > Float.MAX_VALUE){
                    trackingAttributeHeight =  Float.MAX_VALUE;
                }else{
                    trackingAttributeHeight =  Float.valueOf(String.valueOf((double)trackingAttributeListItem));
                }
                setColor(trackingAttributeListItem);
                canvas.drawLine(offset, height - (unit * trackingAttributeHeight), offset, height, paint);
                offset += barWidth;
            }
            addDescription(canvas);
        }
    }

    private void addDescription(Canvas canvas) {
        int width = getMeasuredWidth();
        float height = getMeasuredHeight();

        paint.setTextSize(100);
        paint.setColor(this.getResources().getColor(colorAdditionalBright));
        canvas.drawText(graphDescription, (float) (width*0.5), (float) (height*0.85), paint);
    }


    private void setColor(Double item) {
        if(markExtremes){
            Double lowerExtreme = trackingAttribute.getAverage() - ((trackingAttribute.getAverage() - trackingAttribute.getMin())/2);
            Double upperExtreme = trackingAttribute.getAverage() + ((trackingAttribute.getMax() - trackingAttribute.getAverage())/2);

            if(item >= upperExtreme){
                paint.setColor(this.getResources().getColor(colorPositiveAccent));
            }else if(item <= lowerExtreme){
                paint.setColor(this.getResources().getColor(colorNegativeAccent));
            }else{
                paint.setColor(this.getResources().getColor(colorAdditionalDark));
            }
        }else{
            paint.setColor(this.getResources().getColor(colorAdditionalDark));
        }
    }


}