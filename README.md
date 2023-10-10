<div align="center">
  <img src="https://github.com/neonabuko/resistance-social-network/assets/83613676/df0eaabc-10f3-4b5e-a09b-db7896bce003" alt="Star Wars Resistance Emblem" width="180" height="100">
  <h1>Star Wars Resistance Social Network</h1>
  <h3>A REST API coding challenge</h3>
</div>

> The empire continues its relentless struggle to conquer the galaxy. As a resistance soldier, I have been assigned to
> develop a system for sharing resources among rebels.

### Requirements:
- Docker Compose V2

## Installing:
```shell
git clone https://github.com/neonabuko/resistance-social-network/
    
cd resistance-social-network
    
docker compose up
```
### Implements:
- [x] Spring Web
- [x] Spring Data JPA
- [x] Spring Security
- [x] PostgreSQL
- [x] Docker
- [ ] Liquibase

## Use Cases
- Sign up
- Update location
- Report user
- Trade
- Show:
    1. Percentages of traitors & allies
    2. Average number of items per rebel

## Main Rules
1. Traitors can't trade
2. Traitors can't show up on alliance reports
3. Both parties in a trade must offer the same amount of points

<div align="left">
    <footer>
        <br>
        <p>Contributors:
            <br>
            <a href="https://github.com/neonabuko/">@neonabuko</a>
            <br>
            <a href=https://github.com/alissonspada>@alissonspada</a>
        </p>
    </footer>
</div>
