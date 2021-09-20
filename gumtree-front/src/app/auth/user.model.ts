export class User {
  constructor(
    public email: string,
    public nick: string,
    public token: string,
  ) {
    this.email = email;
    this.nick = nick;
    this.token = token;
  }

}
