import React, { useState } from 'react';
import MainView from './presentation/components/MainView';
import SecondaryView from './presentation/components/SecondaryView';

function App() {
  const [view, setView] = useState<'main' | 'secondary'>('main');

  return (
    <div className="app-container">
      <nav className="glass-nav">
        <h2>🔗 ShortyHex</h2>
        <div>
          <button 
            className={`nav-btn ${view === 'main' ? 'active' : ''}`}
            onClick={() => setView('main')}
          >
            Acortar Enlace
          </button>
          <button 
            className={`nav-btn ${view === 'secondary' ? 'active' : ''}`}
            onClick={() => setView('secondary')}
          >
            Estadísticas
          </button>
        </div>
      </nav>

      <main className="main-content fade-in">
        {view === 'main' ? <MainView /> : <SecondaryView />}
      </main>
    </div>
  );
}

export default App;
