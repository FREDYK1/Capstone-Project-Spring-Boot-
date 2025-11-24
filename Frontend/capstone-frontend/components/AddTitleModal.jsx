export default function AddTitleModal({ isOpen, onClose, onSubmit, newTitle, setNewTitle, isEditing }) {
    if (!isOpen) return null;

    const handleSubmit = (e) => {
        e.preventDefault();
        onSubmit(e);
    };

    return (
        <div className="fixed inset-0 bg-black bg-opacity-30 backdrop-blur-sm flex items-center justify-center z-50">
            <div className="bg-white rounded-lg shadow-lg p-8 max-w-md w-full mx-4">
                <h2 className="text-2xl font-semibold text-gray-800 mb-4">{isEditing ? 'Edit Title' : 'Add New Title'}</h2>
                <form onSubmit={handleSubmit} className="space-y-4">
                    <div>
                        <label className="block text-sm font-medium text-gray-700">Title</label>
                        <input
                            type="text"
                            value={newTitle.title}
                            onChange={(e) => setNewTitle({ ...newTitle, title: e.target.value })}
                            className="mt-1 block w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500"
                            required
                        />
                    </div>
                    <div>
                        <label className="block text-sm font-medium text-gray-700">Title Code</label>
                        <input
                            type="text"
                            value={newTitle.titleCode}
                            onChange={(e) => setNewTitle({ ...newTitle, titleCode: e.target.value })}
                            className="mt-1 block w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500"
                            required
                        />
                    </div>
                    <div>
                        <label className="block text-sm font-medium text-gray-700">Description</label>
                        <textarea
                            value={newTitle.description}
                            onChange={(e) => setNewTitle({ ...newTitle, description: e.target.value })}
                            className="mt-1 block w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500"
                            rows={3}
                            required
                        />
                    </div>
                    <div>
                        <label className="flex items-center">
                            <input
                                type="checkbox"
                                checked={newTitle.isActive}
                                onChange={(e) => setNewTitle({ ...newTitle, isActive: e.target.checked })}
                                className="mr-2"
                            />
                            Active
                        </label>
                    </div>
                    <div className="flex space-x-4">
                        <button type="submit" className="bg-green-600 text-white px-6 py-3 rounded-lg hover:bg-green-700 transition-colors shadow-md">
                            Add Title
                        </button>
                        <button type="button" onClick={onClose} className="bg-gray-600 text-white px-6 py-3 rounded-lg hover:bg-gray-700 transition-colors shadow-md">
                            Cancel
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
}