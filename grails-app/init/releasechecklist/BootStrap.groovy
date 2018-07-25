package releasechecklist

import com.amondel.checklist.ReleaseItem
import com.amondel.checklist.ReleasePackage
import com.amondel.checklist.ReleaseParallelItems
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
        def rel = ReleasePackage.findOrSaveWhere(name:"First Release",startTime: new Date())
        def relSec = ReleaseParallelItems.findOrSaveWhere(releasePackage: rel,description: "Shutting down services",orderNum:2)
        ReleaseItem.findOrSaveWhere(releaseSection: relSec, name: "Shutting down eProof", timeNeeded: 4)
        ReleaseItem.findOrSaveWhere(releaseSection: relSec, name: "Shutting down eEdit", timeNeeded: 5)
        ReleaseItem.findOrSaveWhere(releaseSection: relSec, name: "Shutting down Web Services", timeNeeded: 5)

        def relSecPre = ReleaseParallelItems.findOrSaveWhere(isPreRelease: true, releasePackage: rel,description: "send e-mails",orderNum:1)
        ReleaseItem.findOrSaveWhere(releaseSection: relSecPre, name: "Send A start  E-mail", timeNeeded: 4)

        def relSecPost = ReleaseParallelItems.findOrSaveWhere(isPostRelease: true, releasePackage: rel,description: "send Confirmation e-mails",orderNum:3)
        ReleaseItem.findOrSaveWhere(releaseSection: relSecPost, name: "Send An Confirmation E-mail", timeNeeded: 9)
    }
    def destroy = {
    }
}
