export class User {
    constructor(
        public id: number,
        public login: string,
        public password: string,
        public name: string,
        public email: string,
        public role: string,
        public token: string) {
    }

    reset(): void {
        this.id = null,
        this.login = null,
        this.password = null,
        this.name = null,
        this.email = null,
        this.role = null
    }
}