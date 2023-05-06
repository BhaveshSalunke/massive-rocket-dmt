import {StrictMode} from "react"
import Navbar from "./components/Navbar";
import {UserDataUpload} from "./modules/UserDataUpload";

export default function App() {
	return (
		<StrictMode>
			<Navbar/>
			<main className="p-4 md:px-16 flex justify-end">
				<UserDataUpload/>
			</main>
		</StrictMode>
	)
}
