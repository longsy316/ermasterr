package org.insightech.er.editor.view.figure;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Image;

public class InsertedImageFigure extends Figure {

    private Image image;

    private boolean fixAspectRatio;

    private Dimension imageSize;

    private int alpha;

    public InsertedImageFigure(final Image image, final boolean fixAspectRatio, final int alpha) {
        this.image = image;
        this.fixAspectRatio = fixAspectRatio;
        this.alpha = alpha;
        imageSize = new Dimension(this.image.getBounds().width, this.image.getBounds().height);
    }

    public void setImg(final Image image, final boolean fixAspectRatio, final int alpha) {
        this.image = image;
        this.fixAspectRatio = fixAspectRatio;
        this.alpha = alpha;

        imageSize = new Dimension(this.image.getBounds().width, this.image.getBounds().height);

        this.repaint();
    }

    @Override
    protected void paintFigure(final Graphics graphics) {
        super.paintFigure(graphics);

        graphics.setAlpha(alpha);

        final Rectangle area = getClientArea();

        if (fixAspectRatio) {
            final Rectangle destination = new Rectangle();

            final double dw = (double) imageSize.width / (double) area.width;
            final double dh = (double) imageSize.height / (double) area.height;

            if (dw > dh) {
                // we must limit the size by the width
                destination.width = area.width;
                destination.height = (int) (imageSize.height / dw);

            } else {
                // we must limit the size by the height
                destination.width = (int) (imageSize.width / dh);
                destination.height = area.height;

            }

            destination.x = (area.width - destination.width) / 2 + area.x;
            destination.y = (area.height - destination.height) / 2 + area.y;

            graphics.drawImage(image, new Rectangle(image.getBounds()), destination);

        } else {
            graphics.drawImage(image, new Rectangle(image.getBounds()), area);

        }

    }

}
