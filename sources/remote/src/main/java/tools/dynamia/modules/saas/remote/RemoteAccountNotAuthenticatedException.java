package tools.dynamia.modules.saas.remote;

/*-
 * #%L
 * DynamiaModules - SaaS Remote
 * %%
 * Copyright (C) 2016 - 2019 Dynamia Soluciones IT SAS
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

import tools.dynamia.modules.saas.api.AccountException;

/**
 * Throwed when remote accounts cant check authentication info
 */
public class RemoteAccountNotAuthenticatedException extends AccountException {

    public RemoteAccountNotAuthenticatedException() {
    }

    public RemoteAccountNotAuthenticatedException(String message) {
        super(message);
    }

    public RemoteAccountNotAuthenticatedException(String message, Throwable cause) {
        super(message, cause);
    }
}
