package kevin.le.learnandroid.view.components.spinner;

/**
 * Created by Kevin Le on 2021/11/30.
 */
public class LeSpinnerPojo {
    private boolean isSelected;
    private final String text;

    public LeSpinnerPojo(boolean isSelected, String text) {
        this.isSelected = isSelected;
        this.text = text;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getText() {
        return text;
    }
}
