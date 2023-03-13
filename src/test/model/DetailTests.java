package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DetailTests {
    private Detail detail;

    @BeforeEach
    public void setUp() {
        detail = new Detail("Finish Phase 2 by March 12, 2023 at 10pm");
    }

    @Test
    void testGetDescription() {
        assertEquals("Finish Phase 2 by March 12, 2023 at 10pm", detail.getDescription());
    }

    @Test
    void testToJson() {
        JSONObject jsonObject = detail.toJson();
        assertEquals("Finish Phase 2 by March 12, 2023 at 10pm", jsonObject.getString("description"));
    }
}
