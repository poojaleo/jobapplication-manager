class AuthService {
    getCurrentUsername() {
        const username = sessionStorage.getItem('username');
        if(username === 'undefined' || !username) {
            return null;
        } else {
            return username;
        }
    }

    setUserSession(username) {
        sessionStorage.setItem('username', username);
    }


    resetUserSession() {
        sessionStorage.removeItem('username');
    }


}

export default new AuthService();