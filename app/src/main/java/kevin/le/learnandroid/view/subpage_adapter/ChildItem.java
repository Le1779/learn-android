package kevin.le.learnandroid.view.subpage_adapter;

import kevin.le.learnandroid.model.SubpageData;

public class ChildItem extends ListItem {

    private final SubpageData subpageData;

    public ChildItem(SubpageData subpageData) {
        this.subpageData = subpageData;
    }

    @Override
    int getType() {
        return ItemType.CHILD.ordinal();
    }

    public SubpageData getSubpageData() {
        return subpageData;
    }
}
