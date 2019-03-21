package structure.com.foodportal.models.foodModels;

import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.pchmn.materialchips.ChipsInput;
import com.pchmn.materialchips.model.ChipInterface;

public class ChipsList implements ChipInterface{
    public ChipsList(String chips) {
        this.chips = chips;
    }

    public String getChips() {
        return chips;
    }

    public void setChips(String chips) {
        this.chips = chips;
    }

    String chips;


    @Override
    public Object getId() {
        return null;
    }

    @Override
    public Uri getAvatarUri() {
        return null;
    }

    @Override
    public Drawable getAvatarDrawable() {
        return null;
    }

    @Override
    public String getLabel() {
        return null;
    }

    @Override
    public String getInfo() {
        return null;
    }
}
