package xxx.lunch;

public class Lunch {
    public final String name;
    private boolean checked;

    public Lunch(String name) {
        this.name = name;
    }

    public boolean toggle() {
        checked = !checked;
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isChecked() {
        return checked;
    }

    @Override
    public String toString() {
        return name;
    }
}
