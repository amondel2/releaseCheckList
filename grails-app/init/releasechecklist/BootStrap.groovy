package releasechecklist

import com.amondel.checklist.ReleaseItem
import com.amondel.checklist.ReleasePackage
import com.amondel.checklist.ReleasePackageItems
import com.amondel.checklist.User
import com.amondel.checklist.Role
import com.amondel.checklist.UserRole
import org.hibernate.Hibernate

class BootStrap {

    def init = { servletContext ->

        def ur = Role.findOrSaveWhere authority: "ROLE_USER"
        def ra = Role.findOrSaveWhere authority: "ROLE_ADMIN"
        User admin = User.findByUsername('admin')
        if(!admin){
            admin = new User()
            admin = [password: 'splatt66', enabled: true, accountExpired: false, accountLocked: false]
            admin.username = 'admin'
            admin.save(flush:true,failOnError:true)
        }

        User user = User.findByUsername('aaron')
        if(!user) {
            user = new User()
            user = [password:  'splatt66', enabled: true, accountExpired: false, accountLocked: false]
            user.username  ='aaron'
            user.save(flush:true,failOnError:true)

        }

        UserRole.findOrSaveWhere user: admin, role: ur
        UserRole.findOrSaveWhere user: admin, role: ra
        UserRole.findOrSaveWhere user: user, role: ur
    }
    def destroy = {
    }
}
