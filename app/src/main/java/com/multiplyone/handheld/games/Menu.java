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

package com.multiplyone.handheld.games;

import static com.multiplyone.handheld.SharedData.*;
import static com.multiplyone.handheld.SharedData.MENU_GAME_CHOICE;
import static com.multiplyone.handheld.SharedData.NUMBER_OF_GAMES;
import static com.multiplyone.handheld.SharedData.input;
import static com.multiplyone.handheld.SharedData.mainActivity;
import static com.multiplyone.handheld.SharedData.saveData;
import static com.multiplyone.handheld.SharedData.savedData;
import static com.multiplyone.handheld.SharedData.timerCounter;

import com.multiplyone.handheld.classes.*;

/*
 * Menu: Shows at start up. Player can choose level speed and of course the game
 */

public class Menu extends Game
{
    private int mLevelChoice;
    private int mChoice;
    private int mCounter;
    private int[][] mArray;
    private int[][] mCharacter;
    private int M = 1;

    protected void drawField() {
        //everything is generated using obj() and this will be automatically drawn
    }

    protected void drawField2() {
        //don't show anything on the second field
    }

    protected void onStart() {
        super.mTimerLimit=40;

        mLevelChoice = 1;
    }

    public void input() {
        switch (input) {
            case 1:
                mLevelChoice=(mLevelChoice%9)+1;
                break;
            case 2:
                sSpeed=(sSpeed%9)+1;
                break;
            case 3:
                if (mChoice == 1) mChoice = NUMBER_OF_GAMES; else mChoice--;
                mCounter = 0;
                timerCounter = -1;
                saveData(MENU_GAME_CHOICE, mChoice);
                break;
            case 4:
                mCounter = 0;
                mChoice=(mChoice%NUMBER_OF_GAMES)+1;
                timerCounter = -1;
                saveData(MENU_GAME_CHOICE, mChoice);
                break;
            case 5:
                sCurrentGame = mChoice;
                sLevel = mLevelChoice-1;     //because in nextLevel, the sLevel will be incremented
                nextLevel();
                break;
        }

       // mainActivity.updateUI();

        if (input!=5) {
            mainActivity.updateUI();
            playSound(1);
        }
    }

    protected void calculation() {
        sObjects.clear();

        switch (mChoice) {
            case 1:default:
                gameA();
                break;
            case 2:
                gameB();
                break;
            case 3:
                gameC();
                break;
            case 4:
                gameD();
                break;
            case 5:
                gameE();
                break;
            case 6:
                gameF();
                break;
            case 7:
                gameG();
                break;
            case 8:
                gameH();
                break;
            case 9:
                gameI();
                break;
            case 10:
                gameJ();
                break;
            case 11:
                gameK();
                break;
            case 12:
                gameL();
                break;
            case 13:
                gameM();
                break;
            case 14:
                gameN();
                break;
        }

        for (int i=0; i<mArray.length;i++)
            for (int j=0; j< mArray[i].length;j++)
                if (mArray[i][j]==1)
                    obj(j,10+i);

        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                if (mCharacter[i][j] != 0)
                    obj(2 + j, 1 + i);

        mCounter=(mCounter+1)%4;
    }

    public int getChoice() {
        return mChoice;
    }

    public int getLevelChoice() {
        return mLevelChoice;
    }

    public void load() {
        mChoice = savedData.getInt(MENU_GAME_CHOICE, 1);
    }

    private void gameA() {
        mCharacter = new int[][]{
                {0,M,M,M,0,},
                {M,0,0,0,M,},
                {M,M,M,M,M,},
                {M,0,0,0,M,},
                {M,0,0,0,M,}};

        switch (mCounter) {
            case 0:
                mArray = new int[][]{
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,M,0,0,0,0},
                        {0,0,0,0,M,M,M,0,0,0},
                        {0,0,0,0,M,M,M,0,0,0}};
                break;
            case 1:
                mArray = new int[][]{
                        {0,0,0,0,0,0,0,0,M,M},
                        {0,0,0,0,0,0,0,M,M,0},
                        {0,0,0,0,0,0,0,0,M,M},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,M,0,0,0,0},
                        {0,0,0,0,M,M,M,0,0,0},
                        {0,0,0,0,M,M,M,0,0,0}};
                break;
            case 2:
                mArray = new int[][]{
                        {0,0,0,0,0,0,M,M,0,0},
                        {0,0,0,0,0,M,M,0,0,0},
                        {0,0,0,0,0,0,M,M,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,M,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,M,0,0,0,0},
                        {0,0,0,0,M,M,M,0,0,0},
                        {0,0,0,0,M,M,M,0,0,0}};
                break;
            case 3:
                mArray = new int[][]{
                        {0,0,0,0,0,M,0,M,0,0},
                        {0,0,0,0,0,M,M,M,0,0},
                        {0,0,0,0,0,0,M,0,0,0},
                        {0,0,0,0,0,M,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,M,0,0,0,0},
                        {0,0,0,0,M,M,M,0,0,0},
                        {0,0,0,0,M,M,M,0,0,0}};
                break;
        }
    }

    private void gameB() {
        mCharacter = new int[][]{
                {M,M,M,M,0,},
                {M,0,0,0,M,},
                {M,M,M,M,0,},
                {M,0,0,0,M,},
                {M,M,M,M,0,}};

        switch (mCounter) {
            case 0:
                mArray = new int[][]{
                        {0,M,M,M,M,M,M,M,M,0},
                        {0,0,M,M,M,M,M,M,0,0},
                        {0,0,M,M,M,M,M,M,0,0},
                        {0,M,M,M,M,M,M,M,M,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,M,0,0,0,0,0,0},
                        {0,0,0,M,M,M,M,0,0,0}};
                break;
            case 1:
                mArray = new int[][]{
                        {0,M,M,M,M,M,M,M,M,0},
                        {0,0,M,M,M,M,M,M,0,0},
                        {0,0,M,M,M,M,M,M,0,0},
                        {0,M,M,M,M,M,M,M,M,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {M,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,M,M,M,M,0,0,0,0}};
                break;
            case 2:
                mArray = new int[][]{
                        {0,M,M,M,M,M,M,M,M,0},
                        {0,0,M,M,M,M,M,M,0,0},
                        {0,0,M,M,M,M,M,M,0,0},
                        {0,M,M,M,M,M,M,M,M,0},
                        {0,0,0,M,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,M,M,M,M,0,0,0}};
                break;
            case 3:
                mArray = new int[][]{
                        {0,M,M,M,M,M,M,M,M,0},
                        {0,0,M,M,M,M,M,M,0,0},
                        {0,0,M,M,M,M,M,M,0,0},
                        {0,M,M,0,M,M,M,M,M,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,M,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,M,M,M,M,0,0}};
                break;
        }
    }

    private void gameC() {
        mCharacter = new int[][]{
                {0,M,M,M,M,},
                {M,0,0,0,0,},
                {M,0,0,0,0,},
                {M,0,0,0,0,},
                {0,M,M,M,M,}};

        switch (mCounter) {
            case 0:
                mArray = new int[][]{
                        {0,0,0,M,M,M,M,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,M,M,M,M,M,M,0,0},
                        {0,M,M,M,M,M,M,M,M,0},
                        {0,0,M,M,M,M,M,M,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,M,0,0,0,0},
                        {0,0,0,M,M,M,M,0,0,0}};
                break;
            case 1:
                mArray = new int[][]{
                        {0,0,0,0,M,M,M,M,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,M,M,M,M,M,M,0,0},
                        {0,M,M,M,M,M,M,M,M,0},
                        {0,0,M,M,M,M,M,M,0,0},
                        {0,0,0,0,0,0,0,M,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,M,M,M,M,0,0}};
                break;
            case 2:
                mArray = new int[][]{
                        {0,0,0,0,M,M,M,M,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,M,M,M,M,M,M,0,0},
                        {0,M,M,M,M,M,M,M,M,0},
                        {0,0,M,M,M,M,M,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,M},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,M,M,M,M,0,0}};
                break;
            case 3:
                mArray = new int[][]{
                        {0,0,0,0,0,M,M,M,M,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,M,M,M,M,M,M,0,0},
                        {0,M,M,M,M,M,M,M,M,0},
                        {0,0,M,M,M,M,M,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,M,0},
                        {0,0,0,0,0,M,M,M,M,0}};
                break;
        }
    }

    private void gameD() {
        mCharacter = new int[][]{
                {M,M,M,M,0,},
                {M,0,0,0,M,},
                {M,0,0,0,M,},
                {M,0,0,0,M,},
                {M,M,M,M,0,}};

        switch (mCounter) {
            case 0:
                mArray = new int[][]{
                        {M,M,M,0,0,0,0,M,M,M},
                        {M,M,M,0,0,0,0,M,M,M},
                        {M,M,0,0,0,0,0,0,M,M},
                        {M,0,0,0,M,M,M,0,0,M},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,M,0},
                        {0,0,0,0,0,0,0,M,M,M}};
                break;
            case 1:
                mArray = new int[][]{
                        {M,M,M,0,0,0,0,M,M,M},
                        {M,M,M,0,0,0,0,M,M,M},
                        {M,M,0,0,0,0,0,0,M,M},
                        {M,0,0,M,M,M,0,0,0,M},
                        {0,0,0,0,0,0,0,M,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,M,M,M}};
                break;
            case 2:
                mArray = new int[][]{
                        {M,M,M,0,0,0,0,M,M,M},
                        {M,M,M,0,0,0,0,M,M,M},
                        {M,M,0,0,0,M,0,0,M,M},
                        {M,0,M,M,M,0,0,0,0,M},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,M,M,M}};
                break;
            case 3:
                mArray = new int[][]{
                        {M,M,M,M,0,0,0,M,M,M},
                        {M,M,M,0,0,0,0,M,M,M},
                        {M,M,0,0,0,0,0,0,M,M},
                        {M,0,0,M,M,M,0,0,0,M},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,M,M,M}};
                break;
        }
    }

    private void gameE() {
        mCharacter = new int[][]{
                {M,M,M,M,M,},
                {M,0,0,0,0,},
                {M,M,M,M,0,},
                {M,0,0,0,0,},
                {M,M,M,M,M,}};

        switch (mCounter) {
            case 0:
                mArray = new int[][]{
                        {M,0,0,0,0,0,M,0,0,M},
                        {M,0,0,0,0,M,M,M,0,M},
                        {M,0,0,0,0,0,M,0,0,M},
                        {0,0,0,0,0,M,0,M,0,0},
                        {M,0,0,0,0,0,0,0,0,M},
                        {M,0,0,M,0,0,0,0,0,M},
                        {M,0,M,M,M,0,0,0,0,M},
                        {0,0,0,M,0,0,0,0,0,0},
                        {M,0,M,0,M,0,0,0,0,M},
                        {M,0,0,0,0,0,0,0,0,M}};
                break;
            case 1:
                mArray = new int[][]{
                        {0,0,0,0,0,0,0,0,0,0},
                        {M,0,0,0,0,0,0,0,0,M},
                        {M,0,0,0,0,0,M,0,0,M},
                        {M,0,0,M,0,M,M,M,0,M},
                        {0,0,M,M,M,0,M,0,0,M},
                        {M,0,0,M,0,M,0,M,0,M},
                        {M,0,M,0,M,0,0,0,0,M},
                        {M,0,0,0,0,0,0,0,0,M},
                        {0,0,0,0,0,0,0,0,0,0},
                        {M,0,0,0,0,0,0,0,0,M}};
                break;
            case 2:
                mArray = new int[][]{
                        {M,0,0,0,0,0,0,0,0,M},
                        {0,0,0,M,0,0,0,0,0,0},
                        {M,0,M,M,M,0,0,0,0,M},
                        {M,0,0,M,0,0,0,0,0,M},
                        {M,0,M,0,M,0,M,0,0,M},
                        {0,0,0,0,0,M,M,M,0,0},
                        {M,0,0,0,0,0,M,0,0,M},
                        {M,0,0,0,0,M,0,M,0,M},
                        {M,0,0,0,0,0,0,0,0,M},
                        {0,0,0,0,0,0,0,0,0,0}};
                break;
            case 3:
                mArray = new int[][]{
                        {M,0,M,M,M,0,0,0,0,M},
                        {M,0,0,M,0,0,0,0,0,M},
                        {0,0,M,0,M,0,0,0,0,0},
                        {M,0,0,0,0,0,0,0,0,M},
                        {M,0,0,0,0,0,0,0,0,M},
                        {M,0,0,0,0,0,0,0,0,M},
                        {0,0,0,0,0,0,M,0,0,0},
                        {M,0,0,0,0,M,M,M,0,M},
                        {M,0,0,0,0,0,M,0,0,M},
                        {M,0,0,0,0,M,0,M,0,M}};
                break;
        }
    }

    private void gameF() {
        mCharacter = new int[][]{
                {M,M,M,M,M,},
                {M,0,0,0,0,},
                {M,M,M,M,0,},
                {M,0,0,0,0,},
                {M,0,0,0,0,}};

        switch (mCounter) {
            case 0:
                mArray = new int[][]{
                        {0,M,0,0,0,0,0,M,0,M},
                        {M,M,M,0,0,0,M,M,M,M},
                        {0,M,0,0,0,0,0,M,0,M},
                        {M,0,M,0,0,0,M,0,M,0},
                        {0,0,0,0,0,0,0,0,0,M},
                        {0,0,0,0,M,0,0,0,0,M},
                        {0,0,0,M,M,M,0,0,0,M},
                        {0,0,0,0,M,0,0,0,0,0},
                        {0,0,0,M,0,M,0,0,0,M},
                        {0,0,0,0,0,0,0,0,0,M}};
                break;
            case 1:
                mArray = new int[][]{
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,M},
                        {0,M,0,0,0,0,0,M,0,M},
                        {M,M,M,0,M,0,M,M,M,M},
                        {0,M,0,M,M,M,0,M,0,0},
                        {M,0,M,0,M,0,M,0,M,M},
                        {0,0,0,M,0,M,0,0,0,M},
                        {0,0,0,0,0,0,0,0,0,M},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,M}};
                break;
            case 2:
                mArray = new int[][]{
                        {0,0,0,0,0,0,0,0,0,M},
                        {0,0,0,0,M,0,0,0,0,0},
                        {0,0,0,M,M,M,0,0,0,M},
                        {0,0,0,0,M,0,0,0,0,M},
                        {0,M,0,M,0,M,0,M,0,M},
                        {M,M,M,0,0,0,M,M,M,0},
                        {0,M,0,0,0,0,0,M,0,M},
                        {M,0,M,0,0,0,M,0,M,M},
                        {0,0,0,0,0,0,0,0,0,M},
                        {0,0,0,0,0,0,0,0,0,0}};
                break;
            case 3:
                mArray = new int[][]{
                        {0,0,0,M,M,M,0,0,0,M},
                        {0,0,0,0,M,0,0,0,0,M},
                        {0,0,0,M,0,M,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,M},
                        {0,0,0,0,0,0,0,0,0,M},
                        {0,0,0,0,0,0,0,0,0,M},
                        {0,M,0,0,0,0,0,M,0,0},
                        {M,M,M,0,0,0,M,M,M,M},
                        {0,M,0,0,0,0,0,M,0,M},
                        {M,0,M,0,0,0,M,0,M,M}};
                break;
        }
    }

    private void gameG() {
        mCharacter = new int[][]{
                {0,M,M,M,0,},
                {M,0,0,0,0,},
                {M,0,0,M,M,},
                {M,0,0,0,M,},
                {0,M,M,M,M,}};

        switch (mCounter) {
            case 0:
                mArray = new int[][]{
                        {M,M,0,0,0,0,0,0,M,M},
                        {M,M,0,0,0,0,0,0,M,M},
                        {M,M,0,0,0,0,0,0,M,M},
                        {M,M,0,0,0,0,0,0,M,M},
                        {M,M,M,0,0,0,0,0,0,M},
                        {M,M,M,0,0,0,0,0,0,M},
                        {M,M,M,0,0,M,0,0,0,M},
                        {M,M,M,0,M,M,M,0,0,M},
                        {M,M,M,0,0,M,0,0,0,M},
                        {M,M,M,0,M,0,M,0,0,M}};
                break;
            case 1:
                mArray = new int[][]{
                        {M,M,M,0,0,0,0,0,0,M},
                        {M,M,0,0,0,0,0,0,M,M},
                        {M,M,0,0,0,0,0,0,M,M},
                        {M,M,0,0,0,0,0,0,M,M},
                        {M,M,0,0,0,M,0,0,M,M},
                        {M,M,0,0,M,M,M,0,M,M},
                        {M,M,M,0,0,M,0,0,0,M},
                        {M,M,M,0,M,0,M,0,0,M},
                        {M,M,M,0,0,0,0,0,0,M},
                        {M,M,M,0,0,0,0,0,0,M}};
                break;
            case 2:
                mArray = new int[][]{
                        {M,M,M,0,0,0,0,0,0,M},
                        {M,M,M,0,0,0,0,0,0,M},
                        {M,M,M,0,0,M,0,0,0,M},
                        {M,M,0,0,M,M,M,0,M,M},
                        {M,M,0,0,0,M,0,0,M,M},
                        {M,M,0,0,M,0,M,0,M,M},
                        {M,M,0,0,0,0,0,0,M,M},
                        {M,M,0,0,0,0,0,0,M,M},
                        {M,M,M,0,0,0,0,0,0,M},
                        {M,M,M,0,0,0,0,0,0,M}};
                break;
            case 3:
                mArray = new int[][]{
                        {M,M,0,0,0,M,0,0,M,M},
                        {M,M,0,0,M,M,M,0,M,M},
                        {M,M,M,0,0,M,0,0,0,M},
                        {M,M,M,0,M,0,M,0,0,M},
                        {M,M,M,0,0,0,0,0,0,M},
                        {M,M,0,0,0,0,0,0,M,M},
                        {M,M,0,0,0,0,0,0,M,M},
                        {M,M,0,0,0,0,0,0,M,M},
                        {M,M,0,0,0,0,0,0,M,M},
                        {M,M,0,0,0,0,0,0,M,M}};
                break;
        }
    }

    private void gameH() {
        mCharacter = new int[][]{
                {M,0,0,0,M,},
                {M,0,0,0,M,},
                {M,M,M,M,M,},
                {M,0,0,0,M,},
                {M,0,0,0,M,}};

        switch (mCounter) {
            case 0:
                mArray = new int[][]{
                        {M,M,0,M,0,M,M,M,0,M},
                        {0,0,M,M,M,0,0,M,M,0},
                        {M,M,0,0,M,M,0,M,0,M},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,M,0,0,0,0},
                        {0,0,0,0,M,M,M,0,0,0}};
                break;
            case 1:
                mArray = new int[][]{
                        {M,M,0,M,0,M,M,M,0,M},
                        {0,0,M,M,M,0,0,M,M,0},
                        {M,M,0,0,M,M,0,M,0,M},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,M,0,0,0,0,0,0},
                        {0,0,M,M,M,0,0,0,0,0}};
                break;
            case 2:
                mArray = new int[][]{
                        {M,M,0,M,0,M,M,M,0,M},
                        {0,0,M,M,M,0,0,M,M,0},
                        {M,M,0,0,M,M,0,M,0,M},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,M,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,M,0,0,0,0,0,0},
                        {0,0,M,M,M,0,0,0,0,0}};
                break;
            case 3:
                mArray = new int[][]{
                        {M,M,0,M,0,M,M,M,0,M},
                        {0,0,M,0,M,0,0,M,M,0},
                        {M,M,0,0,M,M,0,M,0,M},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,M,0,0,0,0,0,0},
                        {0,0,M,M,M,0,0,0,0,0}};
                break;
        }
    }

    private void gameI() {
        mCharacter = new int[][]{
                {0,M,M,M,0,},
                {0,0,M,0,0,},
                {0,0,M,0,0,},
                {0,0,M,0,0,},
                {0,M,M,M,0,}};

        switch (mCounter) {
            case 0:
                mArray = new int[][]{
                        {0,M,M,0,M,0,M,M,M,M},
                        {0,0,M,M,M,M,0,0,M,M},
                        {M,M,M,M,M,0,M,M,M,M},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,M,0,0,0,0,0,0},
                        {0,0,M,M,M,0,0,0,0,0}};
                break;
            case 1:
                mArray = new int[][]{
                        {0,M,M,0,M,0,M,M,M,M},
                        {0,0,M,M,M,M,0,0,M,M},
                        {M,M,M,M,M,0,M,M,M,M},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,M,0,0,0,0},
                        {0,0,0,0,M,M,M,0,0,0}};
                break;
            case 2:
                mArray = new int[][]{
                        {0,M,M,0,M,0,M,M,M,M},
                        {0,0,M,M,M,M,0,0,M,M},
                        {M,M,M,M,M,0,M,M,M,M},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,M,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,M,0,0,0,0},
                        {0,0,0,0,M,M,M,0,0,0}};
                break;
            case 3:
                mArray = new int[][]{
                        {0,M,M,0,M,0,M,M,M,M},
                        {0,0,M,M,M,M,0,0,M,M},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,M,0,0,0,0},
                        {0,0,0,0,M,M,M,0,0,0}};
                break;
        }
    }

    private void gameJ() {
        mCharacter = new int[][]{
                {M,M,M,M,M,},
                {0,0,M,0,0,},
                {0,0,M,0,0,},
                {M,0,M,0,0,},
                {0,M,0,0,0,}};

        switch (mCounter) {
            case 0:
                mArray = new int[][]{
                        {0,M,M,M,M,M,M,M,M,0},
                        {0,0,M,M,M,M,M,M,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,M,0,0,0,0,0},
                        {0,0,0,M,M,M,0,0,0,0}};
                break;
            case 1:
                mArray = new int[][]{
                        {0,M,M,M,M,M,M,M,M,0},
                        {0,0,M,M,M,M,M,M,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,M,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,M,0,0,0,0,0},
                        {0,0,0,M,M,M,0,0,0,0}};
                break;
            case 2:
                mArray = new int[][]{
                        {0,M,M,M,M,M,M,M,M,0},
                        {0,0,M,M,M,M,M,0,0,0},
                        {0,0,0,0,M,0,0,0,M,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,M,0,0,0,0},
                        {0,0,0,0,M,M,M,0,0,0}};
                break;
            case 3:
                mArray = new int[][]{
                        {0,M,M,M,M,M,M,M,M,0},
                        {0,0,M,M,0,M,M,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,M},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,M,0,0,0},
                        {0,0,0,0,0,M,M,M,0,0}};
                break;
        }
    }

    private void gameK() {
        mCharacter = new int[][]{
                {M,0,0,0,M,},
                {M,0,0,M,0,},
                {M,M,M,0,0,},
                {M,0,0,M,0,},
                {M,0,0,0,M,}};

        switch (mCounter) {
            case 0:
                mArray = new int[][]{
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,M,M,0,0},
                        {0,0,0,0,0,0,M,M,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,M,0,M,M,M,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0}};
                break;
            case 1:
                mArray = new int[][]{
                        {0,0,M,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,M,M,0,0},
                        {0,0,0,0,0,0,M,M,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,M,M,M,M,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0}};
                break;
            case 2:
                mArray = new int[][]{
                        {0,0,M,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,M,M,0,0},
                        {0,0,0,0,0,0,M,M,0,0},
                        {0,0,M,0,0,0,0,0,0,0},
                        {0,0,M,0,0,0,0,0,0,0},
                        {0,0,M,M,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0}};
                break;
            case 3:
                mArray = new int[][]{
                        {0,0,M,0,0,0,0,0,0,0},
                        {0,0,M,0,0,0,M,M,0,0},
                        {0,0,M,0,0,0,M,M,0,0},
                        {0,0,M,0,0,0,0,0,0,0},
                        {0,0,M,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0}};
                break;
        }
    }

    private void gameL() {
        mCharacter = new int[][]{
                {M,0,0,0,0,},
                {M,0,0,0,0,},
                {M,0,0,0,0,},
                {M,0,0,0,0,},
                {M,M,M,M,0,}};

        switch (mCounter) {
            case 0:
                mArray = new int[][]{
                        {M,M,M,M,M,M,M,M,M,M},
                        {0,0,M,M,0,0,0,0,M,M},
                        {M,M,M,M,M,M,M,M,M,M},
                        {0,0,0,0,M,M,0,0,0,0},
                        {M,M,M,M,M,M,M,M,M,M},
                        {0,M,M,0,0,0,M,M,0,0},
                        {M,M,M,M,M,M,M,M,M,M},
                        {0,0,0,0,M,M,M,M,0,0},
                        {M,M,M,M,M,M,M,M,M,M},
                        {0,0,0,0,0,0,0,0,M,0}};
                break;
            case 1:
                mArray = new int[][]{
                        {M,M,M,M,M,M,M,M,M,M},
                        {0,M,M,0,0,0,0,M,M,M},
                        {M,M,M,M,M,M,M,M,M,M},
                        {0,0,0,M,M,0,0,0,0,M},
                        {M,M,M,M,M,M,M,M,M,M},
                        {M,M,0,0,0,M,M,0,0,0},
                        {M,M,M,M,M,M,M,M,M,M},
                        {0,0,0,M,M,M,M,0,M,0},
                        {M,M,M,M,M,M,M,M,M,M},
                        {0,0,0,0,0,0,0,0,0,0}};
                break;
            case 2:
                mArray = new int[][]{
                        {M,M,M,M,M,M,M,M,M,M},
                        {M,M,M,0,0,0,M,M,M,0},
                        {M,M,M,M,M,M,M,M,M,M},
                        {0,0,0,M,0,0,0,0,M,M},
                        {M,M,M,M,M,M,M,M,M,M},
                        {M,0,0,0,M,M,0,M,0,M},
                        {M,M,M,M,M,M,M,M,M,M},
                        {0,0,M,M,M,M,0,0,0,0},
                        {M,M,M,M,M,M,M,M,M,M},
                        {0,0,0,0,0,0,0,0,0,0}};
                break;
            case 3:
                mArray = new int[][]{
                        {M,M,M,M,M,M,M,M,M,M},
                        {M,0,0,0,0,M,M,M,0,0},
                        {M,M,M,M,M,M,M,M,M,M},
                        {0,M,M,0,0,M,0,M,M,0},
                        {M,M,M,M,M,M,M,M,M,M},
                        {0,0,0,M,M,0,0,0,M,M},
                        {M,M,M,M,M,M,M,M,M,M},
                        {0,M,M,M,M,0,0,0,0,0},
                        {M,M,M,M,M,M,M,M,M,M},
                        {0,0,0,0,0,0,0,0,0,0}};
                break;
        }
    }

    private void gameM() {
        mCharacter = new int[][]{
                {M,0,0,0,M,},
                {M,M,0,M,M,},
                {M,0,M,0,M,},
                {M,0,M,0,M,},
                {M,0,0,0,M,}};

        switch (mCounter) {
            case 0:
                mArray = new int[][]{
                        {0,M,M,0,M,0,0,M,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,M,0,0,M,0,0,M,0,0},
                        {0,M,M,0,M,0,0,M,M,0}};
                break;
            case 1:
                mArray = new int[][]{
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,M,M,0,M,0,0,0,0,0},
                        {0,M,M,0,M,0,0,M,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,M,M,0,M,0,0,M,0,0},
                        {0,M,M,0,M,0,0,M,M,0}};
                break;
            case 2:
                mArray = new int[][]{
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,M,M,0,M,0,0,0,0,0},
                        {0,M,M,0,M,0,0,M,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,M,M,0,M,0,0,M,M,0},
                        {0,M,M,0,M,0,0,M,M,0}};
                break;
            case 3:
                mArray = new int[][]{
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,M,M,0,M,0,0,0,0,0},
                        {0,M,M,0,M,0,0,M,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,M,M,0,M,0,0,0,0,0},
                        {0,M,M,0,M,0,0,M,0,0}};
                break;
        }
    }

    private void gameN() {
        mCharacter = new int[][]{
                {M,0,0,0,M,},
                {M,M,0,0,M,},
                {M,0,M,0,M,},
                {M,0,0,M,M,},
                {M,0,0,0,M,}};


        switch (mCounter) {
            case 0:
                mArray = new int[][]{
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,M,0,0,0},
                        {0,0,0,0,0,0,M,M,0,0},
                        {0,0,0,0,0,0,M,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,M},
                        {M,M,0,0,M,0,0,0,M,M},
                        {M,M,M,M,M,M,0,M,M,M}};
                break;
            case 1:
                mArray = new int[][]{
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,M,0,0,0},
                        {0,0,0,0,0,0,M,M,0,0},
                        {0,0,0,0,0,0,M,0,0,M},
                        {M,M,0,0,M,0,0,0,M,M},
                        {M,M,M,M,M,M,0,M,M,M}};
                break;
            case 2:
                mArray = new int[][]{
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,M,0,0,M},
                        {M,M,0,0,M,0,M,M,M,M},
                        {M,M,M,M,M,M,M,M,M,M}};
                break;
            case 3:
                mArray = new int[][]{
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,M,0,0,M},
                        {M,M,0,0,M,0,M,M,M,M}};
                break;
        }
    }
}
