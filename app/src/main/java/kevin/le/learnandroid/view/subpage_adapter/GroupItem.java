package kevin.le.learnandroid.view.subpage_adapter;

public class GroupItem extends ListItem {

    private final String name;

    public GroupItem(String name) {
        this.name = name;
    }

    @Override
    int getType() {
        return ItemType.GROUP.ordinal();
    }

    public String getName() {
        return name;
    }
}
