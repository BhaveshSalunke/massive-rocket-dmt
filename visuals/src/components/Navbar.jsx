export default function Navbar() {
	return (
		<nav className="border-gray-200 border-b">
			<div className="flex flex-wrap items-center justify-between mx-auto p-4 md:px-16">
				<a className="flex items-center">
					<img src="https://img.logoipsum.com/296.svg" alt="Logo" className="w-8"/>
					<span className="text-xl sm:text-2xl font-medium ml-4">Data Management Tool</span>
				</a>
			</div>
		</nav>
	);
}
