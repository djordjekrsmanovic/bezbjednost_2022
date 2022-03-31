export class AuthenticationResponse {

    jwt: string;
    mail: string;
    role: string;

    constructor(jwt = "", mail = "", role = "") {
        this.jwt = jwt;
        this.mail = mail;
        this.role = role;
    }

    public isAdmin() {
        return this.role === 'ADMIN';
    }

    public isUser() {
        return this.role === 'USER';
    }
   
}