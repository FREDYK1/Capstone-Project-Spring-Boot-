export default function DeleteConfirmationModal({ isOpen, onClose, onConfirm, title }) {
    if (!isOpen) return null;

    return (
        <div className="fixed inset-0 bg-opacity-30 backdrop-blur-sm flex items-center justify-center z-50">
            <div className="bg-white rounded-lg shadow-lg p-8 max-w-md w-full mx-4 justfiy-center">
                <h2 className="text-2xl font-semibold text-gray-800 mb-4">Confirm Deletion</h2>
                <p className="text-gray-700 mb-6 justify-center">Are you sure you want to delete the title "{title}"? This action cannot be undone.</p>
                <div className="flex space-x-4 justify-center">
                    <button onClick={onConfirm} className="bg-red-600 text-white px-6 py-3 rounded-lg hover:bg-red-700 transition-colors shadow-md">
                        Delete
                    </button>
                    <button onClick={onClose} className="bg-gray-600 text-white px-6 py-3 rounded-lg hover:bg-gray-700 transition-colors shadow-md">
                        Cancel
                    </button>
                </div>
            </div>
        </div>
    );
}