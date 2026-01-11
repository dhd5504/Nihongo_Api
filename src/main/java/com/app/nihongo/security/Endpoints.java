package com.app.nihongo.security;

public class Endpoints {
    // Use env FRONTEND_ORIGIN for deployment (e.g., https://your-app.vercel.app); default to local.
    public static final String front_end_host =
            System.getenv().getOrDefault("FRONTEND_ORIGIN", "http://localhost:3000");
}
