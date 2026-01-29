package Geonauts;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

public class ScrollableTextBox extends UIElement implements UIElement.GeoScrollListener {
    private final List<String> lines = new ArrayList<>();
    private double scrollOffset = 0;
    private final double lineHeight = 18;
    private final double padding = 6;
    private final double scrollBarWidth = 8;
    private final Font font = Font.font("Monospaced", 14);

    public ScrollableTextBox(int x, int y, int layer, int height, int width, Renderer parent) {
        super(x, y, layer, height, width, parent);
    }

    public void addLine(String line) {
        lines.add(line);
    }

    public void setText(String text) {
        lines.clear();
        for (String line : text.split("\n", -1)) {
            lines.add(line);
        }
    }

    public void clear() {
        lines.clear();
        scrollOffset = 0;
    }

    @Override
    public void onScroll(double deltaY) {
        scrollOffset -= deltaY;
        clampOffset();
    }

    private double getContentHeight() {
        return lines.size() * lineHeight;
    }

    private double getViewHeight() {
        return height - 2 * padding;
    }

    private void clampOffset() {
        double maxScroll = Math.max(0, getContentHeight() - getViewHeight());
        if (scrollOffset < 0) scrollOffset = 0;
        if (scrollOffset > maxScroll) scrollOffset = maxScroll;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.save();

        // Background
        gc.setFill(Color.rgb(30, 30, 30, 0.85));
        gc.fillRoundRect(x, y, width, height, 6, 6);

        // Border
        gc.setStroke(Color.rgb(80, 80, 80));
        gc.setLineWidth(1);
        gc.strokeRoundRect(x, y, width, height, 6, 6);

        // Clip to text area
        double clipX = x + padding;
        double clipY = y + padding;
        double clipW = width - 2 * padding - scrollBarWidth;
        double clipH = getViewHeight();

        gc.beginPath();
        gc.rect(clipX, clipY, clipW, clipH);
        gc.clip();

        // Draw lines
        gc.setFont(font);
        gc.setFill(Color.WHITE);
        for (int i = 0; i < lines.size(); i++) {
            double lineY = clipY + (i + 1) * lineHeight - scrollOffset;
            if (lineY < clipY - lineHeight) continue;
            if (lineY > clipY + clipH + lineHeight) break;
            gc.fillText(lines.get(i), clipX + 2, lineY);
        }

        gc.restore();

        // Scroll thumb (drawn outside clip)
        double contentH = getContentHeight();
        double viewH = getViewHeight();
        if (contentH > viewH) {
            double trackX = x + width - padding - scrollBarWidth;
            double trackY = y + padding;
            double trackH = viewH;

            double thumbRatio = viewH / contentH;
            double thumbH = Math.max(20, trackH * thumbRatio);
            double scrollRange = contentH - viewH;
            double thumbY = trackY + (scrollOffset / scrollRange) * (trackH - thumbH);

            gc.setFill(Color.rgb(100, 100, 100, 0.6));
            gc.fillRoundRect(trackX, thumbY, scrollBarWidth, thumbH, 4, 4);
        }
    }
}
