package com.nav.dexedd.component.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.nav.dexedd.R;

/**
 * Simple tag view for pokemon types.
 *
 * @author Eduardo Naveda
 * @since 0.0.1
 */
public class TypeTagView extends FrameLayout {

    public static enum Type {

        NONE(0), NORMAL(1), FIGHTING(2), FLYING(3), POISON(4), GROUND(5), ROCK(6), BUG(7), GHOST(8), STEEL(9), FIRE(10),
        WATER(11), GRASS(12), ELECTRIC(13), PSYCHIC(14), ICE(15), DRAGON(16), DARK(17), FAIRY(18), UNKNOWN(10001);

        private Integer type;

        Type(Integer type) {
            this.type = type;
        }

        public static Type getTypeByValue(Integer typeValue) {
            for (Type type : Type.values()) {
                if (type.type == typeValue) {
                    return type;
                }
            }
            return NONE;
        }

        @Override
        public String toString() {
            return type.toString();
        }

    }

    private Boolean noText;
    private TextView typeTagText;

    public TypeTagView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TypeTagView, 0, 0);

        Integer typeValue = 0;
        try {
            noText = a.getBoolean(R.styleable.TypeTagView_no_text, false);
            typeValue = a.getInt(R.styleable.TypeTagView_type, 0);
        }
        finally {
            a.recycle();
        }

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.type_tag_content, this, true);

        typeTagText = (TextView) getChildAt(0);
        if(noText) {
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
        switch (type) {
            case NONE:
                setBackgroundResource(R.drawable.type_tag_background_none);
                typeTagText.setText(getResources().getString(R.string.none));
                break;
            case NORMAL:
                setBackgroundResource(R.drawable.type_tag_background_normal);
                typeTagText.setText(getResources().getString(R.string.normal));
                break;
            case FIGHTING:
                setBackgroundResource(R.drawable.type_tag_background_fighting);
                typeTagText.setText(getResources().getString(R.string.fighting));
                break;
            case FLYING:
                setBackgroundResource(R.drawable.type_tag_background_flying);
                typeTagText.setText(getResources().getString(R.string.flying));
                break;
            case POISON:
                setBackgroundResource(R.drawable.type_tag_background_poison);
                typeTagText.setText(getResources().getString(R.string.poison));
                break;
            case GROUND:
                setBackgroundResource(R.drawable.type_tag_background_ground);
                typeTagText.setText(getResources().getString(R.string.ground));
                break;
            case ROCK:
                setBackgroundResource(R.drawable.type_tag_background_rock);
                typeTagText.setText(getResources().getString(R.string.rock));
                break;
            case BUG:
                setBackgroundResource(R.drawable.type_tag_background_bug);
                typeTagText.setText(getResources().getString(R.string.bug));
                break;
            case GHOST:
                setBackgroundResource(R.drawable.type_tag_background_ghost);
                typeTagText.setText(getResources().getString(R.string.ghost));
                break;
            case STEEL:
                setBackgroundResource(R.drawable.type_tag_background_steel);
                typeTagText.setText(getResources().getString(R.string.steel));
                break;
            case FIRE:
                setBackgroundResource(R.drawable.type_tag_background_fire);
                typeTagText.setText(getResources().getString(R.string.fire));
                break;
            case WATER:
                setBackgroundResource(R.drawable.type_tag_background_water);
                typeTagText.setText(getResources().getString(R.string.water));
                break;
            case GRASS:
                setBackgroundResource(R.drawable.type_tag_background_grass);
                typeTagText.setText(getResources().getString(R.string.grass));
                break;
            case ELECTRIC:
                setBackgroundResource(R.drawable.type_tag_background_electric);
                typeTagText.setText(getResources().getString(R.string.electric));
                break;
            case PSYCHIC:
                setBackgroundResource(R.drawable.type_tag_background_psychic);
                typeTagText.setText(getResources().getString(R.string.psychic));
                break;
            case ICE:
                setBackgroundResource(R.drawable.type_tag_background_ice);
                typeTagText.setText(getResources().getString(R.string.ice));
                break;
            case DRAGON:
                setBackgroundResource(R.drawable.type_tag_background_dragon);
                typeTagText.setText(getResources().getString(R.string.dragon));
                break;
            case DARK:
                setBackgroundResource(R.drawable.type_tag_background_dark);
                typeTagText.setText(getResources().getString(R.string.dark));
                break;
            case FAIRY:
                setBackgroundResource(R.drawable.type_tag_background_fairy);
                typeTagText.setText(getResources().getString(R.string.fairy));
                break;
            default:
                setBackgroundResource(R.drawable.type_tag_background_none);
                typeTagText.setText(getResources().getString(R.string.none));
        }
        invalidate();
    }

}
