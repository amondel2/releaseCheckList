package releasechecklist
import com.amondel.checklist.User
import com.amondel.checklist.Role
import com.amondel.checklist.UserRole

class BootStrap {

    def init = { servletContext ->

        def ur = Role.findOrSaveWhere authority: "ROLE_USER"
        def ra = Role.findOrSaveWhere authority: "ROLE_ADMIN"
        def admin = User.findOrSaveWhere username: 'admin', password:  'splatt66', enabled: true, accountExpired: false, accountLocked: false
        def user = User.findOrSaveWhere username: 'aaron', password:  'splatt66', enabled: true, accountExpired: false, accountLocked: false
        UserRole.findOrSaveWhere user: admin, role: ur
        UserRole.findOrSaveWhere user: admin, role: ra
        UserRole.findOrSaveWhere user: user, role: ur

    }
    def destroy = {
    }
}
