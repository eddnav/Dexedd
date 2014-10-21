package com.nav.dexedd.component.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.nav.dexedd.R;
import com.nav.dexedd.util.TypeUtil;
import com.nav.dexedd.util.TypeUtil.*;

/**
 * Simple tag view for pokemon types.
 *
 * @author Eduardo Naveda
 * @since 0.0.1
 */
public class TypeTagView extends FrameLayout {

    private Boolean noText;
    private TextView typeTagText;

    public TypeTagView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TypeTagView, 0, 0);

        Integer typeValue = 0;
        try {
            noText = a.getBoolean(R.styleable.TypeTagView_no_text, false);
            typeValue = a.getInt(R.styleable.TypeTagView_type, 0);
        } finally {
            a.recycle();
        }

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.type_tag_content, this, true);

        typeTagText = (TextView) getChildAt(0);
        if (noText) {
            typeTagText.setVisibility(GONE);
        }

        Type type = Type.getTypeByValue(typeValue);
        setType(type);
    }

    public void setType(Type type) {
        if (type == Type.NONE) {
            setVisibility(GONE);
        } else {
            setVisibility(VISIBLE);
        }
        setBackgroundResource(getTypeTagBackgroundRes(type));
        typeTagText.setText(getResources().getString(TypeUtil.getTypeNameRes(type)));
    }

    public static int getTypeTagBackgroundRes(Type type) {
        switch (type) {
            case NONE:
                return R.drawable.type_tag_background_none;
            case NORMAL:
                return R.drawable.type_tag_background_normal;
            case FIGHTING:
                return R.drawable.type_tag_background_fighting;
            case FLYING:
                return R.drawable.type_tag_background_flying;
            case POISON:
                return R.drawable.type_tag_background_poison;
            case GROUND:
                return R.drawable.type_tag_background_ground;
            case ROCK:
                return R.drawable.type_tag_background_rock;
            case BUG:
                return R.drawable.type_tag_background_bug;
            case GHOST:
                return R.drawable.type_tag_background_ghost;
            case STEEL:
                return R.drawable.type_tag_background_steel;
            case FIRE:
                return R.drawable.type_tag_background_fire;
            case WATER:
                return R.drawable.type_tag_background_water;
            case GRASS:
                return R.drawable.type_tag_background_grass;
            case ELECTRIC:
                return R.drawable.type_tag_background_electric;
            case PSYCHIC:
                return R.drawable.type_tag_background_psychic;
            case ICE:
                return R.drawable.type_tag_background_ice;
            case DRAGON:
                return R.drawable.type_tag_background_dragon;
            case DARK:
                return R.drawable.type_tag_background_dark;
            case FAIRY:
                return R.drawable.type_tag_background_fairy;
            default:
                return R.drawable.type_tag_background_none;
        }
    }

}
