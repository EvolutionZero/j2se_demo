## master分支
主分支，产品的功能全部实现后，最终在master分支对外发布。

## develop分支
开发分支，基于master分支克隆，产品的编码工作在此分支进行。

## release分支
测试分支，基于delevop分支克隆，产品编码工作完成后，发布到本分支测试，测试过程中发现的小bug直接在本分支进行修复，修复完成后合并到develop分支。本分支属于临时分支，目的实现后可删除分支。

## feature分支
功能特征分支，基于develop分支克隆，主要用于多人协助开发场景或探索性功能验证场景，功能开发完毕后合并到develop分支。feature分支可创建多个，属于临时分支，目的实现后可删除分支

## hotfix分支
Bug修复分支，基于master分支或发布的里程碑Tag克隆，主要用于修复对外发布的分支，收到客户的Bug反馈后，在此分支进行修复，修复完毕后分别合并到develop分支和master分支。本分支属于临时分支，目的实现后可删除分支。

## 流程
### 某项目V1.0迭代至V2.0版本
1. 若新项目，新建空的master分支和develop分支
2. A同事从develop分支拉出feature(A)分支增加A功能
3. B同事从develop分支拉出feature(B)分支增加B功能
4. A、B同事开发完成后合并代码至develop分支，开发经理在合并期间参与代码review
5. 代码合并完成后删除feature(A)、feature(B)分支
6. 从develop分支拉出release分支进行提测，release分支不再引进新功能。
7. 提测后发现release分支中的A与B功能都出现bug，在release分支对bug进行修复再提测，直至测试通过。
8. 测试通过后合并代码至develop分支以及master分支
9. 删除release分支
10. 在master分支上打上tag进行版本发布

### 针对线上高级别bug快速修复方法
1. 从master分支拉出hotfix分支
2. 在hotfix分支上修复测试
3. 修复完成后合并到develop分支以及master分支，在master打上tag发布修复版本
4. 修复完后删除此分支

### 生命周期
1. master : 永久
2. develop ：永久
3. feature ：编码开始到编码开始
4. release ：提测开始到提测结束
5. hotfix : 紧急bug修复开始到结束