import {StrictMode, useState} from "react"
import Navbar from "./components/Navbar";
import CsvUploader from "./components/CsvUploader";

export default function App() {
	const showFileUploader = useState(false);

	return (
		<StrictMode>
			<Navbar/>
			<div className="p-4 md:px-16 flex justify-end">
				<button className="bg-blue-500 rounded-lg px-8 py-4 text-white font-bold"
								onClick={() => showFileUploader[1](true)}>Upload CSV
				</button>
				<CsvUploader displayState={showFileUploader}/>
			</div>
		</StrictMode>
	);
}
