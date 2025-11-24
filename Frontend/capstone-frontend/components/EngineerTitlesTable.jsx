export default function EngineerTitlesTable({ titles, loading, error, onFetch, onEdit, onDelete }) {
    const handleDelete = (item) => {
        onDelete(item);
    };

    if (loading) return <div className="bg-white rounded-lg shadow-md p-6"><p>Loading...</p></div>;
    if (error) return <div className="bg-white rounded-lg shadow-md p-6"><p>Error: {error}</p></div>;

    return (
        <div className="bg-white rounded-lg shadow-md p-6">
            <h2 className="text-2xl font-semibold text-gray-800 mb-4">Engineer Titles</h2>
            <div className="overflow-x-auto">
                <table className="w-full table-fixed">
                <thead>
                    <tr className="bg-gray-50">
                        <th className="p-4 text-left font-medium text-gray-700">Title</th>
                        <th className="p-4 text-left font-medium text-gray-700">Title Code</th>
                        <th className="p-4 text-left font-medium text-gray-700">Description</th>
                        <th className="p-4 text-left font-medium text-gray-700">Status</th>
                        <th className="p-4 text-left font-medium text-gray-700">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {titles.map((item) => (
                        <tr key={item.id} className="border-b border-gray-200 hover:bg-gray-50 transition-colors">
                            <td className="p-4 text-gray-900">{item.title}</td>
                            <td className="p-4 text-gray-900">{item.titleCode}</td>
                            <td className="p-4 text-gray-900">{item.description}</td>
                            <td className="p-4 text-gray-900">
                                <span className={`px-2 py-1 rounded-full text-xs ${item.isActive ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'}`}>
                                    {item.isActive ? 'Active' : 'Inactive'}
                                </span>
                            </td>
                            <td className="p-4">
                                <div className="flex space-x-2">
                                    <button onClick={() => onEdit(item)} className="bg-blue-600 text-white px-3 py-1 rounded-md hover:bg-blue-700 transition-colors shadow-sm text-sm">Edit</button>
                                    <button onClick={() => handleDelete(item)} className="bg-red-600 text-white px-3 py-1 rounded-md hover:bg-red-700 transition-colors shadow-sm text-sm">Delete</button>
                                </div>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
            </div>
        </div>
    );
}