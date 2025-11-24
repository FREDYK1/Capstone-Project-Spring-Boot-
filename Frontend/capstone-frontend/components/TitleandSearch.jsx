export default function TitleandSearch({ onAddClick, searchTerm, onSearchChange }) {
    return (
        <div className="bg-white rounded-lg shadow-md p-8 mb-8">
            <h1 className="text-4xl font-bold text-gray-800 mb-4">
                Engineer Title Management
            </h1>
            <p className="text-gray-600 mb-6 text-lg">
                Manage and organize engineer titles and their ID's within Turntabl
            </p>
            <div className="flex flex-col md:flex-row md:items-center md:justify-between gap-4">
                <div className="relative flex-1">
                    <input
                        type="text"
                        placeholder="Search for titles..."
                        value={searchTerm}
                        onChange={(e) => onSearchChange(e.target.value)}
                        className="w-full p-4 pl-12 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500 focus:border-transparent shadow-sm"
                    />
                    <svg className="absolute left-4 top-4 h-6 w-6 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
                    </svg>
                </div>
                <button onClick={onAddClick} className="bg-green-600 text-white px-6 py-3 rounded-lg hover:bg-green-700 transition-colors shadow-md whitespace-nowrap">
                    Add New Title
                </button>
            </div>
        </div>
    );
}