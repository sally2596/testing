/***
 * 18156kb
 * 208ms
 */
package net.acmicpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.StringTokenizer;

public class baekjoon_9328 {

    private static int h, w, cnt;
    private static char[][] board;
    private static HashSet<Character> keys;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        int[] dx = {0, 1, 0, -1};
        int[] dy = {1, 0, -1, 0};

        int n = Integer.parseInt(bf.readLine());
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(bf.readLine());
            h = Integer.parseInt(st.nextToken());
            w = Integer.parseInt(st.nextToken());

            cnt = 0;

            board = new char[h][];
            for (int j = 0; j < h; j++) {
                board[j] = bf.readLine().toCharArray();
            }

            // 열쇠 해시셋 구성
            keys = getKeySet(bf.readLine().toCharArray());

            Queue<int[]> que = new ArrayDeque<>();
            Queue<int[]> quePossible = new ArrayDeque<>();

            // 입구 찾기
            for (int j = 0; j < h; j++) {
                // 가장자리 세로 두 줄
                if (canGo(j, 0))
                    que.offer(new int[]{j, 0});
                if (canGo(j, w - 1)) // 세로 마지막 줄
                    que.offer(new int[]{j, w - 1});

                if (isDoor(j, 0))
                    quePossible.offer(new int[]{j, 0});
                if (isDoor(j, w - 1))
                    quePossible.offer(new int[]{j, w - 1});
            }
            for (int j = 1; j < w - 1; j++) {
                if (canGo(0, j)) // 맨 윗줄
                    que.offer(new int[]{0, j});
                if (canGo(h - 1, j)) // 맨 아랫줄
                    que.offer(new int[]{h - 1, j});

                if (isDoor(0, j))
                    quePossible.offer(new int[]{0, j});
                if (isDoor(h - 1, j))
                    quePossible.offer(new int[]{h - 1, j});
            }

            while (!que.isEmpty()) {
                // 현재 갈 수 있는 위치 전부 탐색
                while (!que.isEmpty()) {
                    int[] now = que.poll();
                    for (int j = 0; j < 4; j++) {
                        int now_x = now[0] + dx[j];
                        int now_y = now[1] + dy[j];
                        if (inRange(now_x, now_y)) { // 범위 안에 있고
                            if (canGo(now_x, now_y)) // 갈 수 있으면 ( . 이거나 열쇠로 열 수 있거나 열쇠거나)
                                que.offer(new int[]{now_x, now_y});
                            else if (isDoor(now_x, now_y)) { // 문이면 후보군에 넣기
                                quePossible.offer(new int[]{now_x, now_y});
                            }
                        }
                    }
                }
                for (int j = 0; j < quePossible.size(); j++) {

                    int[] now = quePossible.poll();
                    if (canGo(now[0], now[1])) // 갈 수 있으면 ( . 이거나 열쇠로 열 수 있거나 열쇠거나)
                        que.offer(new int[]{now[0], now[1]});
                    else {
                        quePossible.offer(now);
                    }

                }

            }
            sb.append(cnt).append("\n");
        }
        System.out.println(sb);
    }

    private static boolean isDoor(int x, int y) {
        return board[x][y] >= 'A' && board[x][y] <= 'Z';
    }

    private static boolean isKey(int x, int y) {
        return board[x][y] >= 'a' && board[x][y] <= 'z';
    }

    private static boolean inRange(int x, int y) {
        return x >= 0 && x < h && y >= 0 && y < w;
    }

    private static boolean canGo(int x, int y) {
        if (board[x][y] == '.' || keys.contains(Character.toLowerCase(board[x][y]))) { // . 이거나 열쇠로 열 수 있는 곳
            board[x][y] = '*';
            return true;
        }
        if (isKey(x, y)) { // 열쇠있는 곳
            keys.add(board[x][y]);
            board[x][y] = '*';
            return true;
        }
        if (board[x][y] == '$') { // 문서 있는 곳
            cnt++;
            board[x][y] = '*';
            return true;
        }
        return false;
    }

    private static HashSet<Character> getKeySet(char[] list) {
        HashSet<Character> keys = new HashSet<>();
        if (list[0] != '0') {
            for (int j = 0; j < list.length; j++) {
                keys.add(list[j]);
            }
        }
        return keys;
    }
}
