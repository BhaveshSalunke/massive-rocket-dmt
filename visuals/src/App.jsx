import {StrictMode} from "react"
import Navbar from "./components/Navbar"
import Core from "./modules/Core"

export default function App() {
	return (
		<StrictMode>
			<Navbar/>
			<main className="p-4 md:px-16 md:mt-6">
				<Core/>
			</main>
		</StrictMode>
	)
}
