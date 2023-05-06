module.exports = require("vite").defineConfig({
	plugins: [require("@vitejs/plugin-react")()],
	server: {
		open: true,
		port: 3000
	}
});
