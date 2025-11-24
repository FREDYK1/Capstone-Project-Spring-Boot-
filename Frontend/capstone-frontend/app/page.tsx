'use client';

'use client';

import { useState, useEffect } from 'react';

interface Title {
  id: number;
  title: string;
  titleCode: string;
  description: string;
  isActive: boolean;
}
import NavBar from "../components/NavBar.jsx";
import TitleandSearch from "../components/TitleandSearch.jsx";
import EngineerTitlesTable from "../components/EngineerTitlesTable.jsx";
import AddTitleModal from "../components/AddTitleModal.jsx";
import DeleteConfirmationModal from "../components/DeleteConfirmationModal.jsx";
import { getTitles, createTitle, updateTitle, deleteTitle } from '../lib/api';

export default function Home() {
  const [titles, setTitles] = useState<Title[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [showAddForm, setShowAddForm] = useState(false);
  const [editingTitle, setEditingTitle] = useState<Title | null>(null);
  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const [deletingTitle, setDeletingTitle] = useState<Title | null>(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [newTitle, setNewTitle] = useState({
    title: '',
    titleCode: '',
    description: '',
    isActive: true,
  });

  useEffect(() => {
    fetchTitles();
  }, []);

  const fetchTitles = async () => {
    try {
      const data = await getTitles();
      setTitles(data);
    } catch (err) {
      setError((err as Error).message);
    } finally {
      setLoading(false);
    }
  };

  const handleEdit = (title: Title) => {
    setEditingTitle(title);
    setNewTitle({
      title: title.title,
      titleCode: title.titleCode,
      description: title.description,
      isActive: title.isActive,
    });
    setShowAddForm(true);
  };

  const handleDelete = (title: Title) => {
    setDeletingTitle(title);
    setShowDeleteModal(true);
  };

  const confirmDelete = async () => {
    if (!deletingTitle) return;
    try {
      await deleteTitle(deletingTitle.id);
      fetchTitles();
    } catch (err) {
      setError((err as Error).message);
    } finally {
      setShowDeleteModal(false);
      setDeletingTitle(null);
    }
  };

  const filteredTitles = titles.filter(title =>
    title.title.toLowerCase().includes(searchTerm.toLowerCase())
  );

  const handleAddTitle = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      if (editingTitle) {
        await updateTitle(editingTitle.id, newTitle);
      } else {
        await createTitle(newTitle);
      }
      setNewTitle({ title: '', titleCode: '', description: '', isActive: true });
      setEditingTitle(null);
      setShowAddForm(false);
      window.location.reload();
    } catch (err) {
      setError((err as Error).message);
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-gray-50 to-gray-100">
      <NavBar />
      <div className="max-w-7xl mx-auto px-4 py-8">
        <TitleandSearch onAddClick={() => setShowAddForm(true)} searchTerm={searchTerm} onSearchChange={setSearchTerm} />
        <AddTitleModal
          isOpen={showAddForm}
          onClose={() => { setShowAddForm(false); setEditingTitle(null); }}
          onSubmit={handleAddTitle}
          newTitle={newTitle}
          setNewTitle={setNewTitle}
          isEditing={!!editingTitle}
        />
        <EngineerTitlesTable titles={filteredTitles} loading={loading} error={error} onFetch={fetchTitles} onEdit={handleEdit} onDelete={handleDelete} />
        <DeleteConfirmationModal
          isOpen={showDeleteModal}
          onClose={() => setShowDeleteModal(false)}
          onConfirm={confirmDelete}
          title={deletingTitle?.title}
        />
      </div>
    </div>
  );
}
