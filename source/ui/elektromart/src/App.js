import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import login from './pages/login';
import signup from './pages/signup';
import home from './pages/home'
import navbar from './components/navbar';
function App() {
  return (
      <Router>
        <Switch>
          <Route path="/login" component={login} />
          <Route path="/signup" component={signup} />
          // ... other routes
          <Route path="/" component={home} exact />
        </Switch>
      </Router>
  );
}

export default App;
