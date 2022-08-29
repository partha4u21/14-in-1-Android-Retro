/*
* Copyright (C) 2016  Tobias Bielefeld
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see <http://www.gnu.org/licenses/>.
*
* If you want to contact me, send me an e-mail at tobias.bielefeld@gmail.com
*/

package com.multiplyone.handheld.surfaceViews;

import static com.multiplyone.handheld.SharedData.*;
import static com.multiplyone.handheld.classes.Game.*;
import static com.multiplyone.handheld.classes.Game.FIELD_HEIGHT;
import static com.multiplyone.handheld.classes.Game.FIELD_WIDTH;
import static com.multiplyone.handheld.classes.Game.field;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.core.content.ContextCompat;

import com.multiplyone.handheld.R;

/*
 * Surface View for the main Game, it handles the actual drawing
 */

public class GameView1 extends SurfaceView {

    SurfaceHolder ourHolder;
    Paint paint, paintFrame;
    Canvas canvas;

    int p1, p2, p3, p4;
    Paint pBlack;
    Paint pBackground;
    Paint pForeground;


    public GameView1(Context context) {
        super(context);
        ourHolder = getHolder();
        paint = new Paint();
        paintFrame = new Paint();
        pBlack = new Paint();
        pBackground = new Paint();
        pForeground = new Paint();
        pBackground.setColor(Color.WHITE);
        pBlack.setTextSize((float) (width * 1.2));


        paintFrame.setStrokeWidth(1);

        paintFrame.setColor(Color.WHITE);
    }

    public void draw() {
        if (ourHolder.getSurface().isValid()) {
            canvas = ourHolder.lockCanvas();

            //canvas.drawRect(distanceWidth - 3, distanceHeight - 3, distanceWidth + FIELD_WIDTH * width + 3, distanceHeight + FIELD_HEIGHT * width + 3, pBlack);
            canvas.drawRect(distanceWidth - 1, distanceHeight - 1, distanceWidth + FIELD_WIDTH * width + 1, distanceHeight + FIELD_HEIGHT * width + 1, pBackground);

            for (int i=0;i < FIELD_WIDTH;i++)
                for (int j=0;j < FIELD_HEIGHT;j++) {
                    p1 = distanceWidth + i * width;
                    p2 = distanceHeight + j * width;
                    p3 = distanceWidth + (i+1) * width;
                    p4 = distanceHeight + (j+1) * width;

                    if(field[i][j] >= 0) {
                        //default condition to color the foreground and background
                        pForeground.setColor(field[i][j] == 0 ?
                                ContextCompat.getColor(getContext(), R.color.header_grey) :
                                ContextCompat.getColor(getContext(), R.color.marine_blue));
                    } else {
                        //condition to highlight missile/car during gameplay
                        pForeground.setColor(ContextCompat.getColor(getContext(), R.color.orange));
                    }

                    switch (look) {
                        case 1:
                            canvas.drawRect(p1 + 1, p2 + 1, p3 - 1, p4 - 1, pForeground);
                            canvas.drawRect(p1 + width/4, p2 + width/4, p3 - width/4, p4 - width/4, pBackground);
                            canvas.drawRect(p1 + width/4+1, p2 + width/4+1, p3 - (width/4+1), p4 - (width/4+1), pForeground);
                            break;
                        case 2:
                            canvas.drawRect(p1 + 1, p2 + 1, p3 - 1, p4 - 1, pForeground);
                            break;
                        case 3:
                            canvas.drawRect(p1 + 1, p2 + 1, p3 - 1, p4 - 1, pForeground);
                            canvas.drawRect(p1 + width/4, p2 + width/4, p3 - width/4, p4 - width/4, pBackground);
                            canvas.drawRect(p1 + width/4+1, p2 + width/4+1, p3 - width/4, p4 - width/4, pForeground);
                            break;
                    }
                }

            ourHolder.unlockCanvasAndPost(canvas);
        }
    }
}