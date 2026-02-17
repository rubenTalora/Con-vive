import { useState } from 'react';

function App() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');

    // Simulación de login
    setTimeout(() => {
      if (email && password) {
        alert(`¡Login exitoso!\nEmail: ${email}`);
      } else {
        setError('Por favor completa todos los campos');
      }
      setLoading(false);
    }, 1000);
  };

  return (
    <div className="login-container">
      <h2>Con-vive</h2>
      
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Email</label>
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            placeholder="tu@email.com"
            required
          />
        </div>

        <div className="form-group">
          <label>Contraseña</label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            placeholder="••••••••"
            required
          />
        </div>

        {error && <div className="error">{error}</div>}

        <button type="submit" disabled={loading}>
          {loading ? 'Iniciando sesión...' : 'Iniciar sesión'}
        </button>
      </form>

      <div className="register-link">
        ¿No tienes cuenta? <a href="#">Regístrate</a>
      </div>
    </div>
  );
}

export default App;
