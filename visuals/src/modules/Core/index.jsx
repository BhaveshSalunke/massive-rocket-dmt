import UserDataUploader from "./UserDataUploader"
import ProcessStatChecker from "./ProcessStatChecker"
import {useEffect, useState} from "react"
import {GetProcessStats} from "../../api/getProcessStats";

export default function Core() {
	const [processId, setProcessId] = useState()

	const [completedProcessId, setCompletedProcessId] = useState()
	const [processData, setProcessData] = useState()

	useEffect(() => {
		if (completedProcessId) return GetProcessStats(completedProcessId, async (response) => setProcessData((await response.json())))
	}, [completedProcessId])

	console.log(completedProcessId)

	return (<>
			<div className="flex justify-between items-center px-8 md:px-0">
				<h2 className="text-xl md:text-2xl font-semibold">Dashboard</h2>
				<UserDataUploader setProcessId={setProcessId}/>
			</div>
			<div>
				{processId && <ProcessStatChecker processId={processId} onProcessComplete={() => {
					setCompletedProcessId(processId)
					setProcessId(undefined)
				}}/>}
			</div>

			<div className="mt-12 grid grid-cols-1 sm:grid-cols-2 md:grid-cols-4 gap-8 text-left sm:text-center md:text-left ml-8 sm:ml-0">
				<article className="">
					<h3>Total Users</h3>
					<p className="mt-4 text-4xl">
						{processData ? processData["userCount"] : "-"}
					</p>
				</article>
				<article className="">
					<h3>Duplicate Users</h3>
					<p className="mt-4 text-4xl">
						{processData ? processData["duplicateCount"] : "-"}
					</p>
				</article>
			</div>

		</>)
}
