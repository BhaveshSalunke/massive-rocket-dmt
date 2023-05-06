module.exports = require("vite").defineConfig({
	plugins: [require("@vitejs/plugin-react")()],
	server: {
		open: true,
		port: 3000,
		proxy: {
			"/api": {
				target: "http://localhost:8080",
				changeOrigin: true,
			},
		}
	}
});
