export class User {
    constructor(
        public id: number,
        public username: string,
        public password: string,
        public nome: string,
        public email: string,
        public token: string) {
    }
}