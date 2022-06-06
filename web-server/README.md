# web-server

A Clojure library designed to serve request on the web. I'm using http-kit for
the server part and compojure for the routing.

## Usage

- Currently I'm only testing it using the repl. In the repl:
```
web-server.core=> (require '[web-server.core :as w])
nil
web-server.core=> (w/start-server 8080)
#'web-server.core/server
```
- And you can do: `curl http://localhost:8080`
- You should see the web page
- To stop the server in the repl just do
```
web-server.core=> (w/stop-server)
nil
```

## License

Copyright © 2022 FIXME

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
