local roomKey = "cacheKey roomId:" .. ARGV[1]
local currCursorId = ARGV[2]
local size = tonumber(ARGV[3])
local cursorCaches = {}
local nextCursorId
local nextCursorIdPattern = '\"nextCursorId\":\"(.-)\"' -- nextCursorId를 찾기 위한 패턴

for idx = 1, size, 1 do
    local currCursorKey = "\"cursorId: " .. currCursorId .. "\""
    local currChatCursorCache = redis.call('HGET', roomKey, currCursorKey)
    if currChatCursorCache == false then
        break
    end

    cursorCaches[idx] = currChatCursorCache

    local startP, endP, nextCursorIdMatch = currChatCursorCache:find(nextCursorIdPattern)
    if not startP then
        break
    end

    currCursorId = nextCursorIdMatch
end

return cursorCaches
