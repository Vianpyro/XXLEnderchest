package be.locutus.xxlenderchest.config;

/**
 * Represents the configuration for XXL Enderchest.
 *
 * <p>The config file is stored at {@code config/xxlenderchest.json} inside
 * the game (or server) directory and can be reloaded at runtime using
 * {@code /xxlenderchest reload} (OP only).</p>
 */
public class XXLConfig {

    /** Whether the mod is active. When false, the ender chest behaves like vanilla (3 rows). */
    private boolean enabled = true;

    /**
     * Number of rows to display in the ender chest GUI.
     * Valid values: 3 (vanilla), 4, 5, 6.
     * Values outside this range are clamped on load.
     */
    private int rows = 6;

    // -------------------------------------------------------------------------
    // Getters
    // -------------------------------------------------------------------------

    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Returns the configured row count, always clamped to [3, 6].
     */
    public int getRows() {
        return rows;
    }

    // -------------------------------------------------------------------------
    // Setters (used by the config manager after deserialization)
    // -------------------------------------------------------------------------

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    // -------------------------------------------------------------------------
    // Validation
    // -------------------------------------------------------------------------

    /**
     * Clamps {@link #rows} to the allowed range [3, 6] so that invalid config
     * values never reach the mod logic.
     */
    public void validate() {
        if (rows < 3) rows = 3;
        if (rows > 6) rows = 6;
    }

    @Override
    public String toString() {
        return "XXLConfig{enabled=" + enabled + ", rows=" + rows + "}";
    }
}
