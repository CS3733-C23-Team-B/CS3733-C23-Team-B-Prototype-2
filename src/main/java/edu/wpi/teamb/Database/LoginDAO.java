package edu.wpi.teamb.Database;

import java.util.HashMap;
import java.util.Map;

public class LoginDAO {
    private static Map<String, Node> logins = new HashMap<String, Node>();
    public static Map<String, Node> getAllLogins() {
        return logins;
    }
}
