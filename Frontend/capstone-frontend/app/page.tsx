import { useState, useEffect } from 'react';
import NavBar from "../components/NavBar.jsx";
import TitleandSearch from "../components/TitleandSearch.jsx";
import EngineerTitlesTable from "../components/EngineerTitlesTable.jsx";
import { getTitles, searchTitles } from '../lib/api';

export default function Home() {
  const [titles, setTitles] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchTitles();
  }, []);

  const fetchTitles = async () => {
    try {
      const data = await getTitles();
      setTitles(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = async (query) => {
    if (query.trim() === '') {
      await fetchTitles();
    } else {
      try {
        const data = await searchTitles(query);
        setTitles(data);
      } catch (err) {
        setError(err.message);
      }
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-gray-50 to-gray-100">
      <NavBar />
      <div className="max-w-7xl mx-auto px-4 py-8">
        <TitleandSearch onSearch={handleSearch} />
        <EngineerTitlesTable titles={titles} loading={loading} error={error} onFetch={fetchTitles} />
      </div>
    </div>
  );
}
