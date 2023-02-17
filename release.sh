git checkout master
git pull --all

git checkout release
git pull --all

git merge origin/master
git push

git checkout master
