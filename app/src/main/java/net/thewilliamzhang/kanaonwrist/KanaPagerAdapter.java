package net.thewilliamzhang.kanaonwrist;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.LruCache;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by William on 2017-02-04.
 */
public class KanaPagerAdapter extends FragmentGridPagerAdapter {

    private List<List<Fragment>> grid = new ArrayList<>();
    private LruCache<Integer, Drawable> mRowBackgrounds;
    private LruCache<Point, Drawable> mPageBackgrounds;

    public KanaPagerAdapter(final Context context, FragmentManager fm) {
        super(fm);
        int count = 0;
        for (int j = 0; j < Kana.consonants.length(); j ++) {
            List<Fragment> row = new ArrayList<>();
            String[] titles = new String[Kana.vowels.length()];
            String[] hiragana = new String[Kana.vowels.length()];
            String[] katakana = new String[Kana.vowels.length()];
            for (int i = 0; i < Kana.vowels.length(); i ++) {
                titles[i] = Kana.getCorrectRomaji("" + Kana.consonants.charAt(j) + Kana.vowels.charAt(i));
                hiragana[i] = Kana.hiragana[j].substring(i, i + 1);
                katakana[i] = Kana.katakana[j].substring(i, i + 1);
                if (hiragana[i].equals("　")) {
                    titles[i] = "";
                }
                count ++;
                Log.d(Kana.class.getName(), String.format("romaji: %s, kana: %s, %s", titles[i], hiragana[i], katakana[i]));
            }
            WrapperFragment wrapperFragment = new WrapperFragment();
            Bundle bundle = new Bundle();
            bundle.putStringArray("titles", titles);
            bundle.putStringArray("hiragana", hiragana);
            bundle.putStringArray("katakana", katakana);
            wrapperFragment.setArguments(bundle);
            row.add(wrapperFragment);
            grid.add(row);
        }

        this.mPageBackgrounds = new LruCache<Point, Drawable>(grid.size()) {

            private int[] drawableIds = new int[] {
                    R.drawable.bg0,
                    R.drawable.bg1,
                    R.drawable.bg2
            };

            private Drawable[] scaledDrawables = new Drawable[drawableIds.length];

            {
                DisplayMetrics metrics = context.getResources().getDisplayMetrics();
                int height = metrics.heightPixels;
                int width = metrics.widthPixels;
                for (int i = 0; i < drawableIds.length; i ++) {
                    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableIds[i]);
                    Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
                    scaledDrawables[i] = new BitmapDrawable(context.getResources(), scaledBitmap);
                }
                Log.d(KanaPagerAdapter.class.getName(), String.format("Height: %d,\t Width: %d", height, width));
            }

            @Override
            protected Drawable create(final Point page) {
                return scaledDrawables[page.y % scaledDrawables.length];
            }
        };

        this.mRowBackgrounds = new LruCache<Integer, Drawable>(count) {

            @Override
            protected Drawable create(final Integer row) {
                return ContextCompat.getDrawable(context, R.drawable.transparent);
            }
        };
    }
    @Override
    public Drawable getBackgroundForRow(final int row) {
        return mRowBackgrounds.get(row);
    }

    @Override
    public Drawable getBackgroundForPage(final int row, final int column) {
        return mPageBackgrounds.get(new Point(column, row));
    }


    @Override
    public Fragment getFragment(int row, int col) {
        return grid.get(row).get(col);
    }

    @Override
    public int getRowCount() {
        return grid.size();
    }

    @Override
    public int getColumnCount(int row) {
        return grid.get(row).size();
    }

}